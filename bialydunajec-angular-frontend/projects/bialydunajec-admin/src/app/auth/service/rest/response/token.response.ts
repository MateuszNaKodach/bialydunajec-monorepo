export class TokenResponse {
  access_token: string;
  current_user: {
    emailAddress: string,
    username: string
  };
  expires_in: number;
  refresh_token: string;
  scope: string;
  token_type: string;
}
