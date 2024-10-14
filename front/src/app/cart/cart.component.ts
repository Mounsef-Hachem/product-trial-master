import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CartService } from 'app/products/data-access/cart.service';
import { Product } from 'app/products/data-access/product.model';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {
  private readonly cartService = inject(CartService);

  public cartItems:{ product: Product, quantity: number }[] = [];
  totalQuantity: number = 0;

  ngOnInit() {
    this.cartItems = this.cartService.getItems();
  }

  public clearCart() {
    this.cartService.clearCart();
    this.cartItems = [];
  }

  public calculateTotal(): number {
    return this.cartItems.reduce(
      (total, item) => total + (item.product.price * item.quantity), 0
    );
}

public onRemoveFromCart(productId: number) {
  this.cartService.removeFromCart(productId);
  this.cartItems = this.cartService.getItems();  // Refresh the product list
}

public updateCartItemQuantity(product: Product, quantity: number) {
  this.cartService.updateQuantity(product.id, quantity);
  this.cartItems = this.cartService.getItems();  // Refresh cart items
}

}
