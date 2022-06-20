
# dWFMS 

This project contains an example implementation (using Java) of the conceptual dWFMS framework presented in the paper.
In the current state, the purpose of the project is to proof the concept.
Therefore, the concept is implemented in java in the `dwfms.framework` package.
This example implementation is used to implement a client for decentralized process execution in two ways:
- (i) using the Ethereum blockchain and
- (ii) implementing the provided classes and interfaces manually as described in the paper.

The two ways are implemented in the `dwfms.bpm` package and the `dwfms.collaboration` package respectively. In those packages, the framework code is imported.

**NOTE: The intention of the paper/dwfms-project is not to suggest a certain way, concepts or algorithms how decentralized process execution should be implemented, but rather provides a framework, how decentralized process execution systems can be built. Therefore, concepts can be implemented manually or certain artefacts can be integrated (e.g. the Ethereum blockchain).**


This README describes, how to use the provided implementation to demonstrate the concepts.

## Overview.

The following commands must be executed. A detailed description is provided below.

- To demonstrate **Section 6.2. Explicit definition of collaboration components**, follow these steps and watch the output in the terminals
1. Open two terminals `t1`,`t2`
2. In `t1`, run `./gradlew run --args="simple hans 3000"`
3. In `t2`, run `./gradlew run --args="simple peter 4000"`
4.  Let *hans* execute *Start*: `POST localhost:3000/ui/execute` with body `{"instanceRef": "", "task": "Start"}` using [Postman](https://github.com/postmanlabs/postman-app-support/) for instance.
5. Let *peter* execute *B*: `POST localhost:4000/ui/execute` with body `{"instanceRef": "", "task": "B"}`.


- To demonstrate **Section 6.3  Implicit usage of external systems: Ethereum**, follow these steps and watch the output in the terminals
 
1. Open three terminals `t1`,`t2`,`t3` 
2. In `t1`, run `./gradlew run --args="eth hans 3000"`
3. In `t2`, run `./gradlew run --args="eth peter 4000"`
4. In `t3`, run `ganache-cli -l 60000000 -b 15 -d -m "shiver armed industry victory sight vague follow spray couple hat obscure yard"`
5.  Let *hans* deploy a new process instance: `POST localhost:3000/ui/deploy` with body `""`.
6. Let *peter* execute *Start*:  `POST localhost:4000/ui/execute` with body `{"instanceRef": "0x3750be6d627b600d498150cc847a49e3f677c6c7", "task": "Start"}`.

## Detailed description.

### 6.2 Explicit definition of collaboration components

To show the interaction of dWFMS clients using the proposed algorithms of Section 6.2, follow these steps:

1. Open two terminals `t1`,`t2`
2. In `t1`, run `./gradlew run --args="simple hans 3000"`
3. In `t2`, run `./gradlew run --args="simple peter 4000"`

This starts two clients of the dWFMS application. In t1, the hard-coded user _hans_ runs the client on port 3001, and the repsective user interface on port 3000. Hence, we can interact with hans' client by sending requests to localhost:3000.

#### Execute a process model conform task.

 4. Let *hans* execute *Start*: `POST localhost:3000/ui/execute` with body `{"instanceRef": "", "task": "Start"}` using [Postman](https://github.com/postmanlabs/postman-app-support/) for instance.

The clients will then propagate the task execution message. Then, conformance is checked and agreements are sent. If two agreements are received (this is our manual definition of reaching agreement), the local execution machines are updated. This is reflected in the terminals (slightly shortened):

- `t1` receives task execution message

`t1>	Message received: hans wants to execute Start`
`t1>	Action (Start, hans) is model conform, add it to candidate log`
`t1>	send ACK`

- `t1` receives first ACK (from `t1`)

`t1>	Message (ACK) received: hans wants to execute Start`
`t1>	check for agreement: Agreement not reached yet`

- `t2` receives task execution message		

`t2>	Message received: hans wants to execute Start`
`t2>	Action (Start, hans) is model conform, add it to candidate log`
`t2>	send ACK`

- `t1` receives ACK (from `t2`): Agreement is reached
		
`t1>	Message (ACK) received: hans wants to execute Start`
`t1>	check for agreement: Agreement reached`
`t1>	ExecutionMachine updated: (Start, hans) was executed`
	
- `t2` receives ACK (from `t2`)
		
`t2>	Message (ACK) received: hans wants to execute Start`
`t2>	check for agreement: Agreement not reached yet`
		
- `t2` receives ACK from (`t1`): Agreement is reached
		
`t2>	Message (ACK) received: hans wants to execute Start`
`t2>	check for agreement: Agreement reached`
`t2>	ExecutionMachine updated: (Start, hans) was executed`

#### Execute a non-conform task.

After *Start* was executed, *A* must be executed next (cf. the hard-coded process model in `dwfms.ExampleDataFactory`). To simulate a non-conform task, we let *peter* try to execute task *B*.

5. Let *peter* execute *B*: `POST localhost:4000/ui/execute` with body `{"instanceRef": "", "task": "B"}`.

The clients will obtain a negative result from the conformance check, when the task execution is checked. To reduce network load, the clients will not forward the message. However, a malicious client could still send this message, however each honest client will create the following output in the terminal and not forward the message:

`t2>	Message received: peter wants to execute B.`
`t2>	The requested action is not model conform. Processing canceled.`

### 6.3 Implicit usage of external systems: Ethereum

To show the interaction of dWFMS clients using the Ethereum blockchain as described in Section 6.3, follow these steps:

1. Open three terminals `t1`,`t2`,`t3` 
2. In `t1`, run `./gradlew run --args="eth hans 3000"`
3. In `t2`, run `./gradlew run --args="eth peter 4000"`
4. In `t3`, run `ganache-cli -l 60000000 -b 15 -d -m "shiver armed industry victory sight vague follow spray couple hat obscure yard"`

This starts again two clients of the dWFMS application. The third terminal `t3` is used to simulate the blockchian network.

5.  Let *hans* deploy a new process instance: `POST localhost:3000/ui/deploy` with body `""`.

This will instanciate the hard-coded example process model. In future versions, the process model to instanciate must be specified in the HTTP body. The request in Step 5 results in the following output in the terminals:

- The user wants to instanciate a process model and sends the contract-creation request to the blockchain

`t1> Message received in UI-DeploymentHandler. Deploy new process`

- The transaction gets mined.

`t3> eth_sendTransaction`
`t3> eth_getTransactionReceipt`
`t3> Transaction: 0x139bf0e312e7db73d6d8b96a77b22e61d427ad01d47706b7a2e328582daeac6e.
  Contract created: 0x3750be6d627b600d498150cc847a49e3f677c6c7`

- After the contract creation transaction is included in a block, *hans* notifies all participants. They use the contract address to subscribe to events.

`t1> Notify participants.`
`t1> Message received in Deployment Handler...`
`t1> New instance received: Instance(instanceRef=0x3750be6d627b600d498150cc847a49e3f677c6c7)`
`t1> Subscribe for events on: 0x3750be6d627b600d498150cc847a49e3f677c6c7`

`t2> Message received in Deployment Handler...`
`t2> New instance received: Instance(instanceRef=0x3750be6d627b600d498150cc847a49e3f677c6c7)`
`t2> Subscribe for events on: 0x3750be6d627b600d498150cc847a49e3f677c6c7`

6. Let *peter* execute *Start*:  `POST localhost:4000/ui/execute` with body `{"instanceRef": "0x3750be6d627b600d498150cc847a49e3f677c6c7", "task": "Start"}`.

This results in the following terminal output:

- Send the respective event to the blockchain network

`t2> Send Task Execution (Start, peter)`

- Wait until the transaction is included in a block
 
`t3> Transaction: 0x854cd505ba39cc1b9b57e26ae46d9cf36509a16a80a82e7ddf9a29ef65fb8024`

- The event listeners in the dWFMS applications will catch the event

`t1> New event received for instance0x3750be6d627b600d498150cc847a49e3f677c6c7: (Start, 0x4b1184629de85ab53cf86477d190a9f3740abdf5)`
`t1> Acknowledgment received. Machine updated: (Start, peter) was executed.`
`t2> New event received for instance0x3750be6d627b600d498150cc847a49e3f677c6c7: (Start, 0x4b1184629de85ab53cf86477d190a9f3740abdf5)`
`t2> Acknowledgment received. Machine updated: (Start, peter) was executed.`

Therebe, `0x4b1184629de85ab53cf86477d190a9f3740abdf5` is the Ethereum address of *peter* and mapped to the respective user in the dWFMS clients.
