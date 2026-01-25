import { Component, inject, input, OnInit, signal } from '@angular/core';
import { Layout } from '../../../shared/components/layout/layout.component';
import { Loading } from '../../../shared/components/loaging/loading.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ToastService } from '../../../core/services/toast.service';
import { ArtistaService } from '../../../core/services/artista.service';
import { Artista, TipoArtista } from '../../../core/models/artista.model';
import { AlbumService } from '../../../core/services/album.service';

@Component({
  selector: 'app-album-form',
  imports: [
    Layout,
    Loading,
    ReactiveFormsModule,
    RouterLink,
  ],
  templateUrl: './album-form.html',
})
export class AlbumForm implements OnInit {
  private fb = inject(FormBuilder);
  private albumService = inject(AlbumService);
  private artistaService = inject(ArtistaService);
  private toastService = inject(ToastService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  id = input<string>();

  loadingData = signal(true);
  saving = signal(false);
  isEditing = signal(false);
  artistas = signal<Artista[]>([]);
  currentYear = new Date().getFullYear();

  form: FormGroup = this.fb.group({
    artistaId: [null, Validators.required],
    nome: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(300)]],
    anoLancamento: [null, [Validators.min(1900), Validators.max(this.currentYear)]],
    gravadora: [''],
    genero: [''],
    totalFaixas: [null, [Validators.min(1), Validators.max(999)]],
    duracaoTotal: [null, [Validators.min(1)]],
    descricao: ['']
  });

  ngOnInit(): void {
    this.loadArtistas();
    
    const albumId = this.id();
    if (albumId) {
      this.isEditing.set(true);
      this.loadAlbum(Number(albumId));
    }

    // Check for pre-selected artist from query params
    this.route.queryParams.subscribe(params => {
      if (params['artistaId'] && !this.isEditing()) {
        this.form.patchValue({ artistaId: Number(params['artistaId']) });
      }
    });
  }

  private loadArtistas(): void {
    this.artistaService.listar(undefined, 0, 1000).subscribe({
      next: (page) => {
        this.artistas.set(page.content);
        if (!this.isEditing()) {
          this.loadingData.set(false);
        }
      },
      error: () => {
        this.loadingData.set(false);
      }
    });
  }

  private loadAlbum(id: number): void {
    this.albumService.buscarPorId(id).subscribe({
      next: (album) => {
        this.form.patchValue({
          artistaId: album.artistaId,
          nome: album.nome,
          anoLancamento: album.anoLancamento,
          gravadora: album.gravadora,
          genero: album.genero,
          totalFaixas: album.totalFaixas,
          duracaoTotal: album.duracaoTotal,
          descricao: album.descricao
        });
        this.loadingData.set(false);
      },
      error: () => {
        this.loadingData.set(false);
        this.router.navigate(['/albuns']);
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
      ? this.albumService.atualizar(Number(this.id()), data)
      : this.albumService.criar(data);

    request$.subscribe({
      next: () => {
        const message = this.isEditing() 
          ? 'Álbum atualizado com sucesso!' 
          : 'Álbum cadastrado com sucesso!';
        this.toastService.success(message);
        this.router.navigate(['/albuns']);
      },
      error: () => {
        this.saving.set(false);
      }
    });
  }
}
