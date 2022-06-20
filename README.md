# dwfms

Currently, only a proof-of-concept implementation is available to proof the concepts of the dWFMS paper.
However, to prepare the dWFMS framework for future work, a few updates will be committed soon. Thereby, we propose the following roadmap.

## Roadmap

1. Project structure
2. Bug fixes
3. Features

### 1. Project structure

The project structure currently integrates the framework implementation with the implementation of dWFMS applications. In the future, we will decouple the framework implementation from the implementation of dWFMS applications. However, connectors for widely used concepts like Ethereum, can still be provided. 

### 2. Bug fixes

The current implementation is affected from few bugs. However, they are registered and we already work on solutions.

- Infinity loop during process model deployment in simple mode (OK, for proof-of-concept: a default instance is already available)
- Private key is sent (OK, for demonstration purposes)
- RuleEngine isConform has side effects. when an acknowledgement is received, process conformance is checked. (Solution: add facts from the event log when checking conformance)
    
 ### 3. Features
 
 - Sender of ACK is not evaluated yet (Impedes advanced collaboration rules)
 - Integrate further connectors, e.g. Hyperledger, PBFT, ... 
