import { Component, inject, input, OnInit, signal } from '@angular/core';
import { Layout } from '../../../shared/components/layout/layout.component';
import { Loading } from '../../../shared/components/loaging/loading.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ArtistaService } from '../../../core/services/artista.service';
import { ToastService } from '../../../core/services/toast.service';
import { TipoArtista } from '../../../core/models/artista.model';

@Component({
  selector: 'app-artista-form',
  imports: [
    Layout,
    Loading,
    ReactiveFormsModule,
    RouterLink,
  ],
  templateUrl: './artista-form.html',
})
export class ArtistaForm implements OnInit {
  private fb = inject(FormBuilder);
  private artistaService = inject(ArtistaService);
  private toastService = inject(ToastService);
  private router = inject(Router);

  // Route param usando input signal (Angular 16+)
  id = input<string>();

  loadingData = signal(false);
  saving = signal(false);
  isEditing = signal(false);
  currentYear = new Date().getFullYear();

  tiposArtista: { value: TipoArtista; label: string }[] = [
    { value: 'CANTOR', label: 'Cantor(a)' },
    { value: 'BANDA', label: 'Banda' },
    { value: 'DUPLA', label: 'Dupla' },
    { value: 'GRUPO', label: 'Grupo' }
  ];

  form: FormGroup = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(200)]],
    tipo: ['', Validators.required],
    paisOrigem: [''],
    anoFormacao: [null, [Validators.min(1900), Validators.max(this.currentYear)]],
    biografia: ['']
  });

  ngOnInit(): void {
    const artistaId = this.id();
    if (artistaId) {
      this.isEditing.set(true);
      this.loadArtista(Number(artistaId));
    }
  }

  private loadArtista(id: number): void {
    this.loadingData.set(true);
    
    this.artistaService.buscarPorId(id).subscribe({
      next: (artista) => {
        this.form.patchValue({
          nome: artista.nome,
          tipo: artista.tipo,
          paisOrigem: artista.paisOrigem,
          anoFormacao: artista.anoFormacao,
          biografia: artista.biografia
        });
        this.loadingData.set(false);
      },
      error: () => {
        this.loadingData.set(false);
        this.router.navigate(['/artistas']);
      }
    });
  }

  isFieldInvalid(field: string): boolean {
    const control = this.form.get(field);
    return control ? control.invalid && control.touched : false;
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    const data = this.form.value;

    const request$ = this.isEditing()
      ? this.artistaService.atualizar(Number(this.id()), data)
      : this.artistaService.criar(data);

    request$.subscribe({
      next: () => {
        const message = this.isEditing() 
          ? 'Artista atualizado com sucesso!' 
          : 'Artista cadastrado com sucesso!';
        this.toastService.success(message);
        this.router.navigate(['/artistas']);
      },
      error: () => {
        this.saving.set(false);
      }
    });
  }
}
