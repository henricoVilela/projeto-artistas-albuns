import { Routes } from '@angular/router';
import { authGuard, guestGuard } from './core/guards/auth.guard';
import { ArtistaList } from './features/artistas/artista-list/artista-list';
import { ArtistaForm } from './features/artistas/artista-form/artista-form';
import { ArtistaDetail } from './features/artistas/artista-detail/artista-detail';
import { Login } from './features/auth/login/login';
import { Dashboard } from './features/dashboard/dashboard';
import { Register } from './features/auth/register/register';
import { AlbumList } from './features/albuns/album-list/album-list';
import { AlbumForm } from './features/albuns/album-form/album-form';
import { AlbumDetail } from './features/albuns/album-detail/album-detail';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: Login,
        canActivate: [guestGuard]
    },
    {
        path: 'dashboard',
        component: Dashboard,
        canActivate: [authGuard]
    },
    {
        path: 'register',
        component: Register,
        canActivate: [guestGuard]
    },
    {
        path: 'albuns',
        canActivate: [authGuard],
        children: [
            {
                path: '',
                component: AlbumList,
            },
            {
                path: 'novo',
                component: AlbumForm,
            },
            {
                path: ':id',
                component: AlbumDetail,
            },
            {
                path: ':id/editar',
                component: AlbumForm,
            }
        ]
    },
    {
        path: 'artistas',
        canActivate: [authGuard],
        children: [
            {
                path: '',
                component: ArtistaList,
            },
            {
                path: 'novo',
                component: ArtistaForm,
            },
            {
                path: ':id',
                component: ArtistaDetail,
            },
            {
                path: ':id/editar',
                component: ArtistaForm,
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'dashboard'
    }
];
