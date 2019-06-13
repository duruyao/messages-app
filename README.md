# iMessages

## 应用简介
- **名字**: 智能短信（intelligent Messages）
- **选题背景**: 扬正气，促和谐，净化信息环境，提供骚扰拦截功能

## 技术实现
- SMS
- Contacts
- Re
- Broadcast
- ContentProvider
- ContentResolver
- Adapter
- SQLiteDatabase
- Fragement
- FloatingActionButton
- GridLayout
- RecyclerView
- BottomAppBar
- CardView
- ImageButton
- VideoView

---

## 具体展示

- **主页/会话列表页**

与某联系人的所有短消息记录被整理成会话。会话以最后一次通信时间作为会话时间，该页面按会话时间降幂排列。点击会话进入**会话页**。

<img src="img/SessionsActivity.gif" width = "388" height = "690" alt="图片名称" />

---

- **新消息页**

可从联系人中选择消息接收者，可以接收到消息发送状态以及到达状态的广播，若消息发送成功，将跳转至**会话页**。

<img src="img/NewMessageActivity.gif" width = "388" height = "690" alt="图片名称" />

---

- **会话页**

可即时地发送和接收短消息，动态刷新消息列表。可以接收到消息发送状态以及到达状态的广播。

<img src="img/MessagesReadActivity.gif" width = "388" height = "690" alt="图片名称" />

---
