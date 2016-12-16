## stone 脚本语言

通过 《两周自制脚本语言》学习, 制作stone 脚本语言,使用Java实现

不依赖书中GluonJ库, 把eval 方法放到了ASTree 节点中。

v1.0 基本完成了脚本语言的实现

    Token 代表一个单词对象, token 的种类只用三种, 分别是number, string, identifier

    ASTree 代表树节点,子类包括ASTList,ASTLeaf, 及各种实现类节点,包括数组,类,字面常量,数字常量,操作符等等。

    Lexer 词法分析,通过正则表达式解析成三种Token,外加EOF,EOL

    Parser 语法分析库

    BasicParser,FuncParser,ClassParser 是实现基本语法,函数语法,类语法规则的解析器


v2.0 优化性能