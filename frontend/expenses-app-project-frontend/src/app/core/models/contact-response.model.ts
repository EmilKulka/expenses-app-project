import { ContactMessage } from "./contact-message.model";

export interface ContactResponse {
    status: string;
    message: string;
    data: ContactMessage[] | [];
  }
  