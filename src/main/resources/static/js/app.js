// API Configuration
const API_BASE = 'http://localhost:8080/api';

// State
let currentUser = null;
let currentStrategy = 'waste';

// ==================== INITIALIZATION ====================

document.addEventListener('DOMContentLoaded', () => {
    setupNavigation();
    loadProfile();
    loadRecommendations();
    loadPantry();
    setupEventListeners();
});

function setupNavigation() {
    document.querySelectorAll('.nav-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const tab = btn.dataset.tab;
            
            // Update active states
            document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
            document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
            
            btn.classList.add('active');
            document.getElementById(`${tab}-tab`).classList.add('active');
            
            // Load tab content
            if (tab === 'recommendations') loadRecommendations();
            else if (tab === 'pantry') loadPantry();
            else if (tab === 'favorites') loadFavorites();
            else if (tab === 'shopping') loadShoppingList();
        });
    });
}

function setupEventListeners() {
    document.getElementById('refreshRecs').addEventListener('click', () => {
        currentStrategy = document.getElementById('strategySelect').value;
        loadRecommendations();
    });
    
    document.getElementById('addPantryBtn').addEventListener('click', () => {
        document.getElementById('addPantryForm').classList.remove('hidden');
    });
    
    document.getElementById('cancelPantryItem').addEventListener('click', () => {
        document.getElementById('addPantryForm').classList.add('hidden');
        clearPantryForm();
    });
    
    document.getElementById('savePantryItem').addEventListener('click', savePantryItem);
    
    document.getElementById('generateShoppingList').addEventListener('click', loadShoppingList);
    
    // Modal close
    document.querySelector('.close-modal').addEventListener('click', () => {
        document.getElementById('recipeModal').classList.add('hidden');
    });
    
    document.getElementById('recipeModal').addEventListener('click', (e) => {
        if (e.target === document.getElementById('recipeModal')) {
            document.getElementById('recipeModal').classList.add('hidden');
        }
    });
}

// ==================== API CALLS ====================

async function apiCall(endpoint, options = {}) {
    try {
        const response = await fetch(`${API_BASE}${endpoint}`, {
            headers: { 'Content-Type': 'application/json' },
            ...options
        });
        
        if (!response.ok) throw new Error(`API error: ${response.status}`);
        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        showAlert('Unable to connect to server. Make sure backend is running.', 'danger');
        return null;
    }
}

async function showRecipeDetail(recipeName) {
    const modal = document.getElementById('recipeModal');
    const content = document.getElementById('modalContent');
    
    modal.classList.remove('hidden');
    content.innerHTML = '<div class="loading">Loading...</div>';
    
    try {
        const response = await fetch(`${API_BASE}/recipes/${encodeURIComponent(recipeName)}`);
        const recipe = await response.json();
        
        let html = `
            <h2>${recipe.name}</h2>
            <div class="recipe-meta">
                <span>🍽️ ${recipe.baseServings} servings</span>
                <span>🌍 ${recipe.cuisine}</span>
            </div>
            
            <h3>📝 Ingredients</h3>
            <div class="ingredient-list">
        `;
        
        // Add ingredients
        recipe.ingredients.forEach(ing => {
            html += `<span class="ingredient-tag">${ing.quantity} ${ing.unit} ${ing.name}</span>`;
        });
        
        html += `</div><h3>👨‍🍳 Instructions</h3><ol class="recipe-steps">`;
        
        // Add steps - THIS IS THE KEY PART
        if (recipe.steps && recipe.steps.length > 0) {
            recipe.steps.forEach(step => {
                html += `<li><strong>Step ${step.stepNumber}:</strong> ${step.description} <span class="step-time">(${step.durationMinutes} min)</span></li>`;
            });
        } else {
            html += '<li>No instructions available</li>';
        }
        
        html += '</ol>';
        
        content.innerHTML = html;
        
    } catch (error) {
        content.innerHTML = '<p>Could not load recipe</p>';
    }
}

// ==================== PROFILE ====================

async function loadProfile() {
    const profile = await apiCall('/profile');
    if (profile) {
        currentUser = profile;
        document.getElementById('username').textContent = profile.username;
    }
}

// ==================== RECOMMENDATIONS ====================

async function loadRecommendations() {
    const container = document.getElementById('recommendationsList');
    container.innerHTML = '<div class="loading">Loading recommendations...</div>';
    
    const recommendations = await apiCall(`/recommendations?strategy=${currentStrategy}`);
    
    if (!recommendations || recommendations.length === 0) {
        container.innerHTML = '<div class="loading">No recommendations found. Add more items to your pantry!</div>';
        return;
    }
    
    container.innerHTML = recommendations.map(rec => `
        <div class="recipe-card ${rec.wasteReductionScore > 50 ? 'waste-high' : ''} ${rec.wasteReductionScore > 80 ? 'waste-critical' : ''}" 
             onclick="showRecipeDetail('${rec.recipe.name}')">
            <span class="match-score">${rec.wasteReductionScore} pts</span>
            <h3>${rec.recipe.name}</h3>
            <div class="recipe-meta">
                <span>🍽️ ${rec.recipe.baseServings} servings</span>
                <span>⏱️ ${rec.recipe.totalTime} min</span>
            </div>
            <p style="font-size: 14px; color: var(--text-light); margin-bottom: 12px;">
                ${rec.canMake ? '✅ Can make with substitutions' : '⚠️ Missing some ingredients'}
            </p>
            <div class="ingredient-list">
                ${rec.recipe.ingredients.slice(0, 4).map(ing => `
                    <span class="ingredient-tag">${ing.name}</span>
                `).join('')}
                ${rec.recipe.ingredients.length > 4 ? `<span class="ingredient-tag">+${rec.recipe.ingredients.length - 4} more</span>` : ''}
            </div>
            <button class="favorite-btn ${isFavorite(rec.recipe.name) ? 'active' : ''}" 
                    onclick="event.stopPropagation(); toggleFavorite('${rec.recipe.name}')">
                ★
            </button>
        </div>
    `).join('');
    
    // Show expiring items alert
    loadExpiringAlert();
}

async function showRecipeDetail(recipeName) {
    console.log('📖 Showing recipe:', recipeName);
    
    const modal = document.getElementById('recipeModal');
    const content = document.getElementById('modalContent');
    
    if (!modal || !content) return;
    
    content.innerHTML = '<div class="loading">Loading recipe...</div>';
    modal.classList.remove('hidden');
    
    try {
        const response = await fetch(`${API_BASE}/recipes/${encodeURIComponent(recipeName)}`);
        const recipe = await response.json();
        
        console.log('✅ Recipe loaded:', recipe);
        
        // Build ingredients HTML
        let ingredientsHtml = '';
        if (recipe.ingredients) {
            for (const ing of recipe.ingredients) {
                ingredientsHtml += `
                    <span class="ingredient-tag">
                        ${ing.quantity} ${ing.unit} ${ing.name}
                        ${ing.optional ? ' (optional)' : ''}
                    </span>
                `;
            }
        }
        
        // Build steps HTML
        let stepsHtml = '';
        if (recipe.steps && recipe.steps.length > 0) {
            stepsHtml = `
                <h3 style="margin: 20px 0 12px;">👨‍🍳 Cooking Instructions</h3>
                <ol class="recipe-steps">
                    ${recipe.steps.map(step => `
                        <li>
                            <strong>Step ${step.stepNumber}:</strong> ${step.description}
                            <span class="step-time">(${step.durationMinutes} min)</span>
                        </li>
                    `).join('')}
                </ol>
            `;
        }
        
        // Build the complete modal content
        content.innerHTML = `
            <h2 style="margin-bottom: 16px;">${recipe.name}</h2>
            
            <div class="recipe-meta">
                <span>🍽️ ${recipe.baseServings || 2} servings</span>
                <span>⏱️ ${recipe.totalTime || '?'} min</span>
                <span>🌍 ${recipe.cuisine || 'Various'}</span>
            </div>
            
            <h3 style="margin: 20px 0 12px;">📝 Ingredients</h3>
            <div class="ingredient-list">
                ${ingredientsHtml}
            </div>
            
            ${stepsHtml}
        `;
        
    } catch (error) {
        console.error('❌ Error loading recipe:', error);
        content.innerHTML = '<p class="error">Could not load recipe details. Please try again.</p>';
    }
}

// ==================== PANTRY ====================

async function loadPantry() {
    const container = document.getElementById('pantryList');
    container.innerHTML = '<div class="loading">Loading pantry...</div>';
    
    const items = await apiCall('/pantry');
    
    if (!items || items.length === 0) {
        container.innerHTML = '<div class="loading">Your pantry is empty. Add some ingredients!</div>';
        return;
    }
    
    container.innerHTML = items.map(item => `
        <div class="pantry-item ${item.wasteRiskScore > 50 ? 'expiring' : ''} ${item.wasteRiskScore > 80 ? 'expired' : ''}">
            <button class="delete-btn" onclick="deletePantryItem('${item.name}')">🗑️</button>
            <h4>${item.name}</h4>
            <div>
                <span class="quantity">${item.quantity}</span>
                <span class="unit">${item.unit}</span>
            </div>
            <div class="expiry">📅 Expires: ${item.expiryDate}</div>
            ${item.wasteRiskScore > 0 ? `<div class="expiry" style="color: var(--warning);">⚠️ Risk score: ${item.wasteRiskScore}</div>` : ''}
        </div>
    `).join('');
}

async function savePantryItem() {
    const name = document.getElementById('pantryName').value;
    const quantity = parseFloat(document.getElementById('pantryQty').value);
    const unit = document.getElementById('pantryUnit').value;
    const expiryDays = parseInt(document.getElementById('pantryExpiry').value) || 7;
    
    if (!name || !quantity || !unit) {
        showAlert('Please fill in all fields', 'warning');
        return;
    }
    
    await apiCall('/pantry', {
        method: 'POST',
        body: JSON.stringify({ name, quantity, unit, expiryDays })
    });
    
    clearPantryForm();
    document.getElementById('addPantryForm').classList.add('hidden');
    loadPantry();
    showAlert(`${name} added to pantry!`, 'success');
}

async function deletePantryItem(name) {
    await apiCall(`/pantry/${encodeURIComponent(name)}`, { method: 'DELETE' });
    loadPantry();
}

function clearPantryForm() {
    document.getElementById('pantryName').value = '';
    document.getElementById('pantryQty').value = '';
    document.getElementById('pantryUnit').value = '';
    document.getElementById('pantryExpiry').value = '';
}

async function loadExpiringAlert() {
    const container = document.getElementById('expiringAlert');
    const items = await apiCall('/pantry/expiring?days=3');
    
    if (items && items.length > 0) {
        container.innerHTML = `
            <div class="alert alert-warning">
                ⏰ ${items.length} item(s) expiring soon: ${items.map(i => i.name).join(', ')}
            </div>
        `;
    } else {
        container.innerHTML = '';
    }
}

// ==================== FAVORITES ====================

let favorites = [];

async function loadFavorites() {
    const container = document.getElementById('favoritesList');
    const favs = await apiCall('/favorites');
    
    if (!favs || favs.length === 0) {
        container.innerHTML = '<div class="loading">No favorites yet. Star some recipes!</div>';
        return;
    }
    
    favorites = favs.map(f => f.name);
    
    container.innerHTML = favs.map(recipe => `
        <div class="recipe-card" onclick="showRecipeDetail('${recipe.name}')">
            <h3>${recipe.name}</h3>
            <div class="recipe-meta">
                <span>🍽️ ${recipe.baseServings} servings</span>
                <span>🌍 ${recipe.cuisine}</span>
            </div>
            <button class="favorite-btn active" onclick="event.stopPropagation(); toggleFavorite('${recipe.name}')">
                ★
            </button>
        </div>
    `).join('');
}

function isFavorite(recipeName) {
    return favorites.includes(recipeName);
}

async function toggleFavorite(recipeName) {
    await apiCall(`/favorites/${encodeURIComponent(recipeName)}`, { method: 'POST' });
    
    if (isFavorite(recipeName)) {
        favorites = favorites.filter(f => f !== recipeName);
    } else {
        favorites.push(recipeName);
    }
    
    loadRecommendations();
}

// ==================== SHOPPING LIST ====================

async function loadShoppingList() {
    const container = document.getElementById('shoppingList');
    const items = await apiCall('/shopping-list');
    
    if (!items || items.length === 0) {
        container.innerHTML = '<div class="loading">No items needed! Your pantry is well-stocked.</div>';
        return;
    }
    
    container.innerHTML = `
        <div class="shopping-list">
            ${items.map(item => `
                <div class="shopping-item ${item.purchased ? 'checked' : ''}">
                    <span>
                        <input type="checkbox" ${item.purchased ? 'checked' : ''} 
                               onchange="toggleShoppingItem('${item.name}', this.checked)">
                        ${item.name}
                    </span>
                    <span>${item.quantity} ${item.unit}</span>
                </div>
            `).join('')}
        </div>
    `;
}

// ==================== UTILITIES ====================

function showAlert(message, type = 'info') {
    const container = document.getElementById('alertContainer');
    container.innerHTML = `<div class="alert alert-${type}">${message}</div>`;
    
    setTimeout(() => {
        container.innerHTML = '';
    }, 3000);
}

// Make functions available globally for onclick handlers
window.showRecipeDetail = showRecipeDetail;
window.deletePantryItem = deletePantryItem;
window.toggleFavorite = toggleFavorite;