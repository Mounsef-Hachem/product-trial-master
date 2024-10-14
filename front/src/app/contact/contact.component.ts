import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule ],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {

  contactForm: FormGroup;
  successMessage: boolean = false;

  constructor(private fb: FormBuilder) {
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // Email validation
      message: ['', [Validators.required, Validators.maxLength(300)]] // Message validation
    });
  }

  get email() {
    return this.contactForm.get('email');
  }

  get message() {
    return this.contactForm.get('message');
  }

  onSubmit() {
    if (this.contactForm.valid) {
      // Simulate sending the form
      this.successMessage = true;

      // Reset form after submission
      this.contactForm.reset();

      // Hide success message after a certain time
      setTimeout(() => {
        this.successMessage = false;
      }, 3000);
    }
  }
}
