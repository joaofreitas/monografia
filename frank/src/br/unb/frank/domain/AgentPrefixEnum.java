package br.unb.frank.domain;

public enum AgentPrefixEnum {

    MANAGER("manager"), //
    AFFECTIVE("aa"), //
    COGNITIVE("ac"), //
    METACOGNITIVE("am"), //
    WORKGROUP("gt");

    private final String prefix;

    private AgentPrefixEnum(final String prefix) {
	this.prefix = prefix;
    }

    @Override
    public String toString() {
	return prefix;
    }

}
