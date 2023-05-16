JAVAC = javac
JAVAFLAGS = -g

S1_SRC = ServerA/Server.java
S2_SRC = ServerB/Server.java
S3_SRC = ServerC/Server.java
S4_SRC = ServerD/Server.java
S5_SRC = ServerE/Server.java
S6_SRC = ServerF/Server.java
S7_SRC = ServerG/Server.java
S8_SRC = ServerH/Server.java


all: s1 s2 s3 s4 s5 s6 s7 s8

s1:
	$(JAVAC) $(JAVAFLAGS) $(S1_SRC)

s2:
	$(JAVAC) $(JAVAFLAGS) $(S2_SRC)

s3:
	$(JAVAC) $(JAVAFLAGS) $(S3_SRC)

s4:
	$(JAVAC) $(JAVAFLAGS) $(S4_SRC)

s5:
	$(JAVAC) $(JAVAFLAGS) $(S5_SRC)

s6:
	$(JAVAC) $(JAVAFLAGS) $(S6_SRC)

s7:
	$(JAVAC) $(JAVAFLAGS) $(S7_SRC)

s8:
	$(JAVAC) $(JAVAFLAGS) $(S8_SRC)


clean:
	rm -f ServerA/*.class ServerB/*.class ServerC/*.class ServerD/*.class ServerE/*.class ServerF/*.class ServerG/*.class ServerH/*.class
	rm -f ServerA/*.txt ServerB/*.txt ServerC/*.txt ServerD/*.txt ServerE/*.txt ServerF/*.txt ServerG/*.txt ServerH/*.txt