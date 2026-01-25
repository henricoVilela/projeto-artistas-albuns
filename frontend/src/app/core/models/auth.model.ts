export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  nome: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
  username: string;
}

export interface RefreshRequest {
  refreshToken: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  nome: string;
}
