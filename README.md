# RpnToSource
Late night project I hacked up in 2 hours as preperation for my decompiler project,
this is a really simple expression decompiler that takes in an rpn representation and outputs valid source code,
I will expend on it in the future

Initial version supports: Binary expressions and literals

Motivation:
I had to come up with an expression decompiler for a decompiler for a stack based language, 
in its nature the bytecode is very similar to rpn so i assumed all bytecode expression code as valid rpn code
and converted it to an ast, and from there, to valid source code.

The pipeline:
Main is used to get an RPN expression from the console 
forwards the RPN to a lexer to produce tokens ->
token printer in main ->
Tokens to ast ->
ast printer in main ->
Ast to source code ->
source code print in main.

The algorithm:
This is a very basic algorithm I came up with on 3 hours of sleep
The grouping inferring algorithm will probably be precedence anomaly detection, with inspiration from the shunting yard algorithm,
but backwards.
(I will expend on this in the future)

Will support:
All kinds of expressions and grouping inferring (The bytecode has no grouping, but the original source code does)




Input/Output Examples:

input: 3 5 4 * + 15 3 * + 2 -
output: 3 + 5 * 4 + 15 * 3 - 2

(Will remove redundent parentheses in the future)
