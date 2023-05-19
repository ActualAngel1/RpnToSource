# RpnToSource
Late night project I hacked up in 2 hours as preperation for my decompiler project,
this is a really simple expression decompiler that takes in an rpn representation and outputs valid source code,
I will expend on it in the future

Initial version supports: Binary expressions and literals
Now supports grouping inferring, unary and more binary expressions.


Motivation:
---
I had to come up with an expression decompiler for a decompiler for a stack based language, 
in its nature the bytecode is very similar to rpn so i assumed all bytecode expression code as valid rpn code
and converted it to an ast, and from there, to valid source code.


The pipeline:
---
Main is used to get an RPN expression from the console 
forwards the RPN to a lexer to produce tokens ->
token printer in main ->
Tokens to ast ->
ast printer in main ->
Ast to source code ->
source code print in main.


The algorithm:
---
This is a very basic algorithm I came up with on 3 hours of sleep
Parsing into an ast:
from left to right each token is checked to be:

A: A literal, pushing a literal Expression node into the stack

B: A unary, identified (- or !), popping an expression from the stack and creating a unary expression from it and the current token

C: A binary, popping two Expression nodes from the stack and merging them into a new, Binary node.

(More types in the future)


As for the grouping inferring:
If a subexpression of a binary node happen to be of lower precedence, that means a precedence anomaly has occured and we wrap that expression in a grouping expression.


Will support:
---
Or/And expressions, working with actual bytecode.



Input/Output Examples:
---
input: 3 5 4 * + 15 3 * + 2 -

output: 3 + 5 * 4 + 15 * 3 - 2

-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

input: 3 5 + 10 *

output: (3 + 5) * 10             # infers grouping

-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

input: 3 10 + 6 10 + *

output: (3 + 10) * (6 + 10)      # infers grouping

-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

input: 8 7 + 11 * 4 3 + > ! 

output: !((8 + 7) * 11 > 4 + 3)  # infers grouping
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

Original: - ((8+9)* 11 - 9 + 5);

input: 8 9 + 11 * 9 - 5+ -

output: -((8 + 9) * 11 - 9 + 5) # infers grouping
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
