import {
  Component,
  inject,
  OnInit,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CartService } from "./products/data-access/cart.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent],
})
export class AppComponent implements OnInit{
  private readonly cartService = inject(CartService);
  
  title = "ALTEN SHOP";
  totalQuantity: number = 0;

  ngOnInit() {
    // Subscribe to totalQuantity$ to keep totalQuantity updated
    this.cartService.totalQuantity$.subscribe(quantity => {
      this.totalQuantity = quantity;
    });
  }

}
