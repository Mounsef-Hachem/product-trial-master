import { CommonModule } from "@angular/common";
import { Component, inject, signal, ViewChild} from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { PaginatorModule } from "primeng/paginator";
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table'; 
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { ProductsService } from "app/products/data-access/products.service";
import { CartService } from "app/products/data-access/cart.service";
const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [
    CommonModule,  
    MatTableModule, 
    PaginatorModule,
    MatSortModule,
    MatPaginatorModule,
    MatFormFieldModule,
    DataViewModule, 
    CardModule, 
    ButtonModule, 
    DialogModule, 
    ProductFormComponent,
    MatInputModule,

  ],
})
export class ProductListComponent {
  
  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);

  products: Product[] = []; // Original products list
  filteredProducts: Product[] = []; // Filtered products list

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);


  ngOnInit() {
    
    this.productsService.get().subscribe((_products) => {
      this.filteredProducts = _products;
      this.products = _products;
    });
    
  }

  public filterProducts(event: any) {

    let searchTerm = event.target.value.trim();

    if (!searchTerm) {
      this.products = this.filteredProducts; // If no search term, show all products

    } else {
      this.products = this.filteredProducts.filter(product =>
        product.name.toLowerCase().includes(searchTerm.toLowerCase())
      );

    }
  }


  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe(() => {
      this.productsService.get().subscribe((products) => {
        this.filteredProducts = products;
      });
    });
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe(() => {
        this.productsService.get().subscribe((products) => {
          this.filteredProducts = products;
        });
      });
    } else {
      this.productsService.update(product).subscribe(() => {
        this.productsService.get().subscribe((products) => {
          this.filteredProducts = products;
        });
      });
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  onAddToCart(product: Product) {
    this.cartService.addToCart(product, product.quantity);
    this.updateProductQuantity(product)
  }

  public updateProductQuantity(product: Product) {
    this.cartService.updateQuantity(product.id,product.quantity);
  }
  
}


