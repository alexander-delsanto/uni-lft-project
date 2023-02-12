JC=javac
JFLAGS=-d bin -cp src/
JCOMPILE=$(JC) $(JFLAGS)
INPUT=&& touch bin/input.lft

lexer:
	$(JCOMPILE) src/part_2_lexer/Lexer.java $(INPUT)

parser_1:
	$(JCOMPILE) src/part_3_parser_1/Parser.java $(INPUT)

parser_2:
	$(JCOMPILE) src/part_3_parser_2/Parser.java $(INPUT)

evaluator:
	$(JCOMPILE) src/part_4_evaluator/Evaluator.java $(INPUT)

translator_1:
	$(JCOMPILE) src/part_5_1_translator/Translator.java $(INPUT)

translator_2:
	$(JCOMPILE) src/part_5_2_boolean/Translator.java $(INPUT)

clear:
	rm -r bin/