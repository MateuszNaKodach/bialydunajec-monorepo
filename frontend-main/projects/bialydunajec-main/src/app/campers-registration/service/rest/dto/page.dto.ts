export class PageDto<T> {
  content: T[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  'number': number;
  numberOfElements: number;
  first: boolean;
}
