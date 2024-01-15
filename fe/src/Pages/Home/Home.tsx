import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./Home.module.scss";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { ConversationData, Messages, UserData } from "../../Types/types";
import {
  getConversations,
  getUserData,
  getMessages,
} from "../../Components/Helpers/Fetching/FetchingFunctions";

const Home = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState<UserData | null>(null);
  const [conversations, setConversations] = useState<ConversationData[] | null>(
    null
  );
  const [messages, setMessages] = useState<Messages[] | null>(null);
  const [textareaContent, setTextareaContent] = useState("");
  const [selectedConversation, setSelectedConversation] =
    useState<ConversationData | null>(null);

  const socket = new SockJS("/chat");
  const stompClient = Stomp.over(socket);
  var subscription: any = null;

  function disconnect() {
    stompClient.connect(
      {},
      () => {
        console.log("Connected");
      },
      () => {
        console.log("Connection failed");
      }
    );
    console.log(stompClient);
    if (stompClient !== null) {
      stompClient.disconnect(() => {
        console.log("Disconnected");
      }, {});
    }
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        setUserData(await getUserData());
      } catch (error: any) {
        console.error("Error fetching user data:", error.message);
      }
      try {
        setConversations(await getConversations());
      } catch (error: any) {
        console.error("Error fetching user data:", error.message);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (selectedConversation) {
          const messages = await getMessages(selectedConversation.id);
          setMessages(messages);
        }
      } catch (error: any) {
        console.error("Error fetching messages:", error.message);
      }
    };

    fetchData();
  }, [selectedConversation]);

  const handleLogout = async () => {
    try {
      const response = await fetch("/logout", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        disconnect();
        navigate("/");
      } else {
        const errorText = await response.text();
        console.error("Logout failed:", errorText);
      }
    } catch (error: any) {
      console.error("An error occurred during logout:", error.message);
    }
  };

  const setSelected = (conversation: ConversationData) => {
    setConversations((prevConversations) => {
      if (prevConversations) {
        const updatedConversations = prevConversations.map((conv) => ({
          ...conv,
          selected: conv.id === conversation.id,
        }));
        return updatedConversations;
      }
      return prevConversations;
    });
    setSelectedConversation(conversation);
    subscribe(conversation.id);
    const textItem = document.getElementsByTagName("textarea");
    textItem[0].id = conversation.id.toString();
  };

  function subscribe(selectedConversationId: number) {
    if (subscription) {
      subscription.unsubscribe();
    }
    subscription = stompClient.subscribe(
      `/topic/${selectedConversationId}`,
      (response) => {
        setMessages((prevMessages) => [
          ...(prevMessages || []),
          JSON.parse(response.body),
        ]);
      }
    );
  }

  const sendMessage = () => {
    if (selectedConversation) {
      const newMessage: Messages = {
        id: 0,
        conversationId: selectedConversation.id,
        userId: userData!.id,
        messageText: textareaContent,
      };

      stompClient.send(
        `/topic/${selectedConversation.id}`,
        {},
        JSON.stringify(newMessage)
      );
      setMessages((prevMessages) => [...(prevMessages || []), newMessage]);
    } else {
      console.error("No conversation selected.");
    }
  };
  return (
    <div className={styles.main}>
      <div className={styles.main__header}>
        <div className={styles.main__header__chats}>
          {conversations?.map((conversation) => (
            <div
              key={conversation.id}
              className={`${styles.main__header__chats__conversation} ${
                conversation.selected ? styles.selected : ""
              }`}
              onClick={() => setSelected(conversation)}
            >
              {conversation.conversationName}
            </div>
          ))}
        </div>
        <div className={styles.main__header__container}>
          {userData && <div>{userData.nickname}</div>}
          <button type="button" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </div>
      <div className={styles.main__bodyContainer}>
        <div className={styles.main__bodyContainer__chatBox}>
          {messages?.map((message, idx) => (
            <div key={idx}>
              <div>{message.userId}</div>
              <div>{message.messageText}</div>
            </div>
          ))}
        </div>
        <div className={styles.main__bodyContainer__messageBox}>
          <textarea
            id={selectedConversation?.id?.toString() || ""}
            value={textareaContent}
            onChange={(e) => setTextareaContent(e.target.value)}
          ></textarea>
          <button onClick={sendMessage}>Send</button>
        </div>
      </div>
    </div>
  );
};

export default Home;
