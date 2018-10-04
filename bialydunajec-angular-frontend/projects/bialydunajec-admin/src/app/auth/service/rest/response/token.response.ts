export class TokenResponse {
  access_token: string;
  current_user: TokenCurrentUser;
  expires_in: number;
  refresh_token: string;
  scope: string;
  token_type: string;
}

export class TokenCurrentUser {
  userId: string;
  emailAddress: string;
  username: string;
}
