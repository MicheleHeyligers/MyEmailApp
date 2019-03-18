package com.example.s11.myemailclient;

/**
 * Created by ass on 18.11.2017.
 */
public class EmailProvider implements java.io.Serializable {

	private String name;
	private int receivingPort;
	private String receivingHost;
	private String receivingProtocol;
	private int sendingPort;
	private String sendingHost;
	private String sendingProtocol;

	public EmailProvider(String name, int receivingPort, String receivingHost, String receivingProtocol, int sendingPort, String sendingHost, String sendingProtocol) {

		this.name = name;
		this.receivingPort = receivingPort;
		this.receivingHost = receivingHost;
		this.receivingProtocol = receivingProtocol;
		this.sendingPort = sendingPort;
		this.sendingHost = sendingHost;
		this.sendingProtocol = sendingProtocol;
	}
	// hier nur getter, keine Setter, damit nicht versehentlich was verstellt wird
	public String getName() {
		return this.name;
	}

	public int getReceivingPort() {
		return this.receivingPort;
	}

	public String getReceivingHost() {
		return this.receivingHost;
	}

	public String getReceivingProtocol() {
		return this.receivingProtocol;
	}

	public int getSendingPort() {
		return this.sendingPort;
	}

	public String getSendingHost() {
		return this.sendingHost;
	}

	public String getSendingProtocol() {
		return this.sendingProtocol;
	}

}
