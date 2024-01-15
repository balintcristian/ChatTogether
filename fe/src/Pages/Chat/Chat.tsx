import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

interface Conversation {
  id: number;
  conversationName: string;
}

interface MessageDTO {
  userId: string;
  messageText: string;
  sentDatetime: string;
}

const Chat: React.FC = () => {
  const [selectedConversationId, setSelectedConversationId] =
    useState<number>(-1);
  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [conversations, setConversations] = useState<Conversation[]>([]);
  const [messages, setMessages] = useState<MessageDTO[]>([]);
  const [inputMessage, setInputMessage] = useState<string>("");
  const [subscription, setSubscription] = useState<Stomp.Subscription | null>(
    null
  );
  const navigate = useNavigate();
  useEffect(() => {
    fetchConversations();
    connectWebSocket();

    return () => {
      disconnectWebSocket();
    };
  }, []);
  async function handleLoggout() {
    try {
      const response = await fetch("/logout");

      if (response.ok) {
        navigate("/");
      } else {
        const errorText = await response.text();
        console.error("Login failed:", errorText);
      }
    } catch (error: any) {
      console.error("An error occurred during login:", error.message);
    }
  }
  const fetchConversations = async () => {
    try {
      const response = await fetch("/conversations/get");
      if (response.ok) {
        const conversationsData: Conversation[] = await response.json();
        setConversations(conversationsData);
      } else {
        console.error("Error fetching conversations");
      }
    } catch (error) {
      console.error("Error fetching conversations:", error);
    }
  };

  const connectWebSocket = () => {
    const socket = new SockJS("/chat");
    const stompClient = Stomp.over(socket);

    stompClient.connect(
      {},
      () => {
        console.log("Connected");
        setStompClient(stompClient);
      },
      () => {
        console.log("Connection failed");
      }
    );
  };

  const disconnectWebSocket = () => {
    if (stompClient) {
      unsubscribeWebSocket(); // Ensure subscription is unsubscribed
      stompClient.disconnect(() => {
        console.log("Disconnected");
      }, {});
      setStompClient(null);
    }
  };

  const subscribeWebSocket = () => {
    if (stompClient && selectedConversationId !== -1) {
      const newSubscription = stompClient.subscribe(
        `/topic/${selectedConversationId}`,
        (response) => {
          showMessage(JSON.parse(response.body));
        }
      );
      setSubscription(newSubscription);
    }
  };

  const unsubscribeWebSocket = () => {
    if (subscription) {
      subscription.unsubscribe();
      setSubscription(null);
    }
  };

  const showMessage = (messageDTO: MessageDTO) => {
    setMessages((prevMessages) => [...prevMessages, messageDTO]);
  };

  const handleConversationClick = (conversationId: number) => {
    setSelectedConversationId(conversationId);
    setMessages([]); // Clear messages for the selected conversation
    unsubscribeWebSocket(); // Unsubscribe from previous conversation
    subscribeWebSocket(); // Subscribe to the new conversation
    fetchMessages(conversationId);
  };

  const fetchMessages = async (conversationId: number) => {
    try {
      const response = await fetch(`/messages/get/${conversationId}`);
      if (response.ok) {
        const messagesData: MessageDTO[] = await response.json();
        setMessages(messagesData);
      } else {
        console.error("Error fetching messages");
      }
    } catch (error) {
      console.error("Error fetching messages:", error);
    }
  };

  const handleSendMessage = () => {
    if (stompClient) {
      stompClient.send(
        `/app/sendMessage/${selectedConversationId}`,
        {},
        JSON.stringify({
          userId: "someUserId", // Replace with actual user ID
          messageText: inputMessage,
        })
      );
    }

    setInputMessage("");
  };

  return (
    <div>
      <div>
        <h2>Conversations</h2>
        <ul>
          {conversations.map((conversation) => (
            <li
              key={conversation.id}
              onClick={() => handleConversationClick(conversation.id)}
            >
              {conversation.conversationName}
            </li>
          ))}
        </ul>
      </div>

      {selectedConversationId !== -1 && (
        <div>
          <h2>Messages</h2>
          <div className="message-container">
            {messages.map((message, index) => (
              <div key={index}>
                <strong>{message.userId}:</strong> {message.messageText}
              </div>
            ))}
          </div>

          <div>
            <input
              type="text"
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
            />
            <button onClick={handleSendMessage}>Send</button>
          </div>
        </div>
      )}
      <button onClick={handleLoggout}>Logout</button>
    </div>
  );
};

export default Chat;
