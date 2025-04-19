Here’s a concise and technical `README.md` for your **Consistency Algorithms in Replicated Database Systems** repository, complete with badges and clear instructions:

---

# Consistency Algorithms in Replicated Database Systems  
[![ForTheBadge built-with-love](http://ForTheBadge.com/images/badges/built-with-love.svg)](https://github.com/BhanuPrakashNani/)  
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/BhanuPrakashNani/)  

**Implementation of Strict, Causal, Sequential, PRAM, and Independent consistency models for replicated databases in Java.**  

---

## 🚀 Overview  
This project demonstrates **five consistency algorithms** for replicated database systems:  
1. **Strict Consistency**  
2. **Causal Consistency**  
3. **Sequential Consistency**  
4. **PRAM (Pipelined RAM) Consistency**  
5. **Independent Consistency**  

Built using Java RMI and MySQL, it simulates multi-client interactions with replicated servers to validate each model’s behavior under concurrent operations.  

---

## ⚙️ Prerequisites  
- Java 8+  
- MySQL Server  
- `mysql-connector-java-8.0.19.jar` (included in `src/`)  

---

## 🛠️ How to Run  
### 1. Update `CLASSPATH`  
Ensure the MySQL connector JAR and current directory are in your `CLASSPATH`:  
```bash  
export CLASSPATH=.:./mysql-connector-java-8.0.19.jar  
```  

### 2. Open Five Terminals  
#### Terminal 1: Start RMI Registry  
```bash  
rmiregistry -J-Djava.rmi.useLocalHostName=true -J-Djava.rmi.server.hostname=127.0.0.1  
```  

#### Terminals 2 & 3: Launch Database Servers  
```bash  
cd src/  
java Server   # Primary server  
java Server2  # Replica server  
```  

#### Terminals 4 & 5: Start Clients  
```bash  
cd src/  
java Client   # Client 1  
java Client2  # Client 2  
```  

---

## 📂 Repository Structure  
```  
src/  
├── Server.java          # Primary server (implements RMI)  
├── Server2.java         # Replica server  
├── Client.java          # Client 1 logic  
├── Client2.java         # Client 2 logic  
├── ConsistencyModel.java # Interface for all algorithms  
├── StrictConsistency.java  
├── CausalConsistency.java  
├── SequentialConsistency.java  
├── PRAMConsistency.java  
├── IndependentConsistency.java  
└── mysql-connector-java-8.0.19.jar  
```  

---

## 🔍 Key Features  
- **RMI-based communication** between clients and replicated servers.  
- **MySQL backend** for persistent storage.  
- **Validation scripts** to verify each consistency model’s guarantees.  
- **Thread-safe operations** for concurrent client requests.  

---

## 📜 Citation  
If you use this work, please link to the repository:  
```  
https://github.com/BhanuPrakashNani/Consistency_In_Replicated_DB_Systems  
```  

---

## 🤝 Contributing  
Pull requests are welcome! For major changes, open an issue first.  

---

## 📄 License  
MIT © [Bhanu Prakash Poluparthi](https://linkedin.com/in/bhanu-prakash-poluparthi)  

--- 

Let me know if you’d like to add:  
- A diagram of the RMI architecture.  
- Sample output logs for each consistency model.  
- Instructions for Docker deployment.
