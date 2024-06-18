package artcreator.creator.impl;

import artcreator.domain.port.Domain;
import artcreator.statemachine.port.StateMachine;

public class CreatorImpl {

	private StateMachine stateMachine;
	private Domain domain;

	public CreatorImpl(StateMachine stateMachine, Domain domain) {
		this.domain = domain;
		this.stateMachine = stateMachine;
	}

	public void sysop(String str) {
		System.out.println(str);
	}

	public void importImage(String path){
		System.out.println("some shit");
	}

	public void chooseTemplateType(String type){
		System.out.println("some shit");	
	}
}
