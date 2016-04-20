
JFLAGS = -g
JC = javac

.SUFFIXES: .java .class


.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
        Toy.java \
        Table.java \
        Attribute.java \
        Record.java 



default: classes

classes: $(CLASSES:.java=.class)
	
clean:
	$(RM) *.class 