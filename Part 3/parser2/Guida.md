GUIDA(<em>\<prog> -> \<statlist></em>) = {assign, print, read, while, conditional, '{'}

GUIDA(<em>\<statlist> -> \<stat>\<statlistp></em>) = {assign, print, read, while, conditional, '{' }

GUIDA(<em>\<statlistp> -> ;\<stat>\<statlistp></em>) = { ';' }

GUIDA(<em>\<statlistp> -> $\varepsilon$</em>) = {EOF, '}' }

GUIDA(<em>\<stat> -> assign\<expr>to\<idlist></em>) = {assign}

GUIDA(<em>\<stat> -> print[\<exprlist>]</em>) = {print}

GUIDA(<em>\<stat> -> read[\<idlist>]</em>) = {read}

GUIDA(<em>\<stat> -> while(\<bexpr>)\<stat></em>) = {while}

GUIDA(<em>\<stat> -> conditional[\<optlist>]\<statp></em>) = {conditional}

GUIDA(<em>\<stat> -> {\<statlist>}</em>) = { '{' }

GUIDA(<em>\<statp> -> end</em>) = {end}

GUIDA(<em>\<statp> -> else\<stat>end</em>) = {else}

GUIDA(<em>\<idlist> -> ID\<idlistp></em>) = {ID}

GUIDA(<em>\<idlistp> -> , ID\<idlistp></em>) = { ',' }

GUIDA(<em>\<idlistp> -> $\varepsilon$</em>) = {EOF, ';', ']', '}', end, option }

GUIDA(<em>\<optlist> -> \<optitem>\<optlistp></em>) = {option}

GUIDA(<em>\<optlistp> -> \<optitem>\<optlistp></em>) = {option}

GUIDA(<em>\<optlistp> -> $\varepsilon$</em>) = { ']' }

GUIDA(<em>\<optitem> -> option(\<bexpr>)do\<stat></em>) = {option}

GUIDA(<em>\<bexpr> -> RELOP\<expr>\<expr></em>) = {RELOP}

GUIDA(<em>\<expr> -> +(\<exprlist>)</em>) = { '+' }

GUIDA(<em>\<expr> -> \*(\<exprlist>)</em>) = { '\*' }

GUIDA(<em>\<expr> -> -\<expr>\<expr></em>) = { '-' }

GUIDA(<em>\<expr> -> -\<expr>\<expr></em>) = { '/' }

GUIDA(<em>\<expr> -> NUM</em>) = { NUM }

GUIDA(<em>\<expr> -> ID</em>) = { ID }

GUIDA(<em>\<exprlist> -> \<expr>\<exprlistp></em>) = { '+', '-', '*', '/', NUM, ID }

GUIDA(<em>\<exprlistp> -> , \<expr>\<exprlistp></em>) = { ',' }

GUIDA(<em>\<exprlistp> ->  $\varepsilon$</em>) = { ')', ']' }
