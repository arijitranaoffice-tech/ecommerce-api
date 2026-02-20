#!/bin/bash

# Script to push the e-commerce API to GitHub
# Run this after creating the repository on GitHub

REPO_URL="https://github.com/arijitranaoffice-tech/ecommerce-api.git"

echo "=== Pushing E-commerce API to GitHub ==="
echo ""

# Check if GitHub CLI is available
if command -v gh &> /dev/null; then
    echo "GitHub CLI detected. Creating repository..."
    gh repo create arijitranaoffice-tech/ecommerce-api --public --source=. --remote=origin --push
    echo ""
    echo "✓ Repository created and code pushed successfully!"
else
    echo "GitHub CLI not found. Please follow these steps:"
    echo ""
    echo "1. Go to https://github.com/new"
    echo "2. Create a new repository named 'ecommerce-api'"
    echo "3. Owner: arijitranaoffice-tech"
    echo "4. Keep it Public or Private as per your preference"
    echo "5. DO NOT initialize it with README, .gitignore, or license"
    echo "6. Click 'Create repository'"
    echo ""
    echo "7. Then run these commands in the terminal:"
    echo ""
    echo "   cd '/home/rana/Springboot Projects/ecommerce-api'"
    echo "   git remote set-url origin https://github.com/arijitranaoffice-tech/ecommerce-api.git"
    echo "   git push -u origin main"
    echo ""
    echo "8. Enter your GitHub credentials when prompted"
    echo ""
    echo "   OR use a Personal Access Token:"
    echo "   git push -u origin main"
    echo "   (Use your PAT as password)"
    echo ""
fi

echo ""
echo "Repository URL will be: https://github.com/arijitranaoffice-tech/ecommerce-api"
