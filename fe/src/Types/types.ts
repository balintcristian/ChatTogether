export interface UserData {
  loginUsername: string;
  nickname: string;
  id: number;
}
export interface ConversationData {
  conversationName: string;
  id: number;
  selected: Boolean;
}
export interface MessageData {
  id: number;
  conversationId: number;
  userId: number;
  messageText: string;
}
export interface AuthContextType {
  isAuthenticated: boolean;
  signin: (
    loginUsername: string,
    password: string,
    callback: VoidFunction
  ) => void;
  signout: (callback: VoidFunction) => void;
}
