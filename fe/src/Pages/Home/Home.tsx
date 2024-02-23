import { useEffect, useState } from "react";
import styles from "./Home.module.scss";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { ConversationData, MessageData, UserData } from "../../Types/types";
import {
  getConversations,
  getUserData,
  getMessages,
} from "../../Components/Helpers/Fetching/FetchingFunctions";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState<UserData | null>(null);
  const [conversations, setConversations] = useState<ConversationData[] | null>(
    null
  );
  const [messages, setMessages] = useState<MessageData[] | null>(null);
  const [textareaContent, setTextareaContent] = useState("");
  const [selectedConversation, setSelectedConversation] =
    useState<ConversationData | null>(null);

  const socket = new SockJS("/chat");
  const stompClient = Stomp.over(socket);
  var subscription = stompClient.subscriptions;
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
          setMessages(await getMessages(selectedConversation.id));
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
        if (stompClient !== null) {
          stompClient.disconnect(() => {
            console.log("Disconnected");
          }, {});
        }
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
    if (selectedConversation && subscription) {
      stompClient.unsubscribe(selectedConversation?.id.toString());
    }
    stompClient.subscribe(`/topic/${selectedConversationId}`, (response) =>
      appendMessage(response.body)
    );
  }

  const appendMessage = (message: any) => {
    setMessages((prevMessages) => [
      ...(prevMessages || []),
      JSON.parse(message),
    ]);
  };
  const sendMessage = async () => {
    const messageToSend = {
      conversationId: selectedConversation?.id,
      messageText: textareaContent,
    };
    try {
      const response = await fetch("/messages/save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(messageToSend),
      });
      console.log(JSON.stringify(messageToSend));
      if (response.status === 201) {
        stompClient.send(
          `/topic/${selectedConversation?.id}`,
          {},
          JSON.stringify(messageToSend)
        );
        const textarea = document.getElementsByTagName("textarea");
        textarea[0].value = "";
        if (selectedConversation)
          setMessages(await getMessages(selectedConversation.id));
      } else {
        console.error("Error:", response.statusText);
      }
    } catch (error) {
      console.error("Error:", error);
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
            <div
              key={idx}
              className={styles.main__bodyContainer__chatBox__message}
            >
              <div>{message.userId}: </div>
              <div> {message.messageText}</div>
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
