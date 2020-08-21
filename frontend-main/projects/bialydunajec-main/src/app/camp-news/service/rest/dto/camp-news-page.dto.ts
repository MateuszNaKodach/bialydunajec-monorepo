export class CampNewsPageDto {
  content: CampNewsDto[];
  paging: PagingCursorsDto;
}

export class NewsPagingDto {
  cursors: PagingCursorsDto;
}

export class PagingCursorsDto {
  before: string;
  after: string;
}

export class CampNewsDto {
  id: string;
  message: string;
  picture: NewsPictureDto;
  createdDate: Date;
}

export class NewsPictureDto {
  url: string;
  name: string;
  description: string;
}
