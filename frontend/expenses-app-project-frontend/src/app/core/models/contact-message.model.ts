export interface ContactMessage {
    id: string;
    type: string;
    subject: string;
    content: string;
    createdAt: string;
    resolved: boolean;
    replyContent?: string;
  }