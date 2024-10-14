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
      existingProduct.quantity += quantity; // Add the selected quantity
  } else {
      this.items.push({ product, quantity }); // Add a new item with the chosen quantity
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
      // If the updated quantity is greater than 0, we update the quantity
      if (quantity > 0) {
        productToUpdate.quantity = quantity;
      } else {
        // If the quantity is equal to or less than 0, the product is removed from the cart
        this.removeFromCart(productId);
      }
      this.updateTotalQuantity(); // Updates the total quantity in the cart
    } else {
      console.log(`The product with ID ${productId} is not in the cart.`);
    }
  }


}
