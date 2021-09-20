## Intelie - Event Store Challenge

> **Challenge proposed by Intelie to assess proficiency with the tool and best practices.**

The folder structure that was implemented was designed in the DDD model, which makes it easy to identify each class and what its responsibilities are.

> In the class responsible for querying, adding and removing, a validation is performed before performing any type of operation, informing the user to forget to enter some important information to continue the events. <br/> <br />
> In the methods that I thought would need more robustness in order to receive a larger amount of data, some thread safe modes were implemented to improve its performance. <br/> <br />
> In the query, addition and removal methods, comments were added for those who read it, understand what that validation is for or something like that.