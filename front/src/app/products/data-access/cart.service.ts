import { Injectable } from '@angular/core';
import { Product } from './product.model';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private items: { product: Product, quantity: number }[] = [];
  private totalQuantitySubject = new BehaviorSubject<number>(0); // Observable for total quantity
  totalQuantity$ = this.totalQuantitySubject.asObservable(); // Expose it as an observable
  private cartItems = new BehaviorSubject<Product[]>([]);
  cartItems$ = this.cartItems.asObservable();

  addToCart(product: Product, quantity: number) {

    const existingProduct = this.items.find(item => item.product.id === product.id);
    
    if (existingProduct) {
      existingProduct.quantity += quantity; // Ajouter la quantité sélectionnée
  } else {
      this.items.push({ product, quantity }); // Ajouter un nouvel élément avec la quantité choisie
  }
    this.updateTotalQuantity(); // Update the total quantity
    console.log(`${product.name} has been added to the cart.`);
  }

  getItems() {
    this.updateTotalQuantity(); // Update the total quantity
    return this.items;
  }

  clearCart() {
    this.items = [];
    this.updateTotalQuantity(); // Update the total quantity
    return this.items;
  }

  removeFromCart(productId: number) {
    this.items = this.items.filter(item => item.product.id !== productId);
    this.updateTotalQuantity(); // Update the total quantity
    console.log(`Product with id ${productId} has been removed from the cart.`);
  }

  updateTotalQuantity() {
    console.log("Total qte. : "+this.items.reduce((total, item) => total + item.quantity, 0));
    const total = this.items.reduce((sum, item) => sum + item.quantity, 0);
    this.totalQuantitySubject.next(total); // Emit the new total quantity
  }

  updateQuantity(productId: number, quantity: number) {
    const productToUpdate = this.items.find(item => item.product.id === productId);
  
    if (productToUpdate) {
      // Si la quantité mise à jour est supérieure à 0, on met à jour la quantité
      if (quantity > 0) {
        productToUpdate.quantity = quantity;
      } else {
        // Si la quantité est égale ou inférieure à 0, on retire le produit du panier
        this.removeFromCart(productId);
      }
      this.updateTotalQuantity(); // Met à jour la quantité totale dans le panier
    } else {
      console.log(`Le produit avec l'ID ${productId} n'est pas dans le panier.`);
    }
  }
  

}
