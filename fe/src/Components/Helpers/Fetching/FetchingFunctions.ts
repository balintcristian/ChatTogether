// Import necessary types
import { ConversationData, Messages, UserData } from "../../../Types/types";

// Function to fetch user data
export async function getUserData(): Promise<UserData | null> {
  try {
    const response = await fetch("/user/get", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (response.ok) {
      const jsonData: UserData = await response.json();
      return jsonData;
    } else {
      console.error("Fetching user data failed:", await response.text());
      return null;
    }
  } catch (error: any) {
    console.error("An error during fetch:", error.message);
    return null;
  }
}

export async function getConversations(): Promise<ConversationData[] | null> {
  try {
    const response = await fetch("/conversations/get", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (response.ok) {
      const jsonData: ConversationData[] = await response.json();
      return jsonData;
    } else {
      console.error("Fetching conversations failed:", await response.text());
      return null;
    }
  } catch (error: any) {
    console.error("An error during fetch:", error.message);
    return null;
  }
}

export async function getMessages(
  conversationId: number
): Promise<Messages[] | null> {
  try {
    const response = await fetch(`/messages/get/${conversationId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (response.ok) {
      const jsonData: Messages[] = await response.json();
      return jsonData;
    } else {
      console.error("Fetching messages failed:", await response.text());
      return null;
    }
  } catch (error: any) {
    console.error("An error during fetch:", error.message);
    return null;
  }
}
