package ebs.ewttest.client;

import ebs.ewt.client.ewt.EWTDataDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dubov Aleksey
 * Date: Oct 15, 2009
 * Time: 5:08:20 PM
 * Company: EBS (c) 2009
 */

public class PersoneDTO extends EWTDataDTO {
	private Integer id;
	private String login;
	private String name;
	private Integer owner;
	private Integer level;
	private List<Integer> levels;
	private Boolean blocked;

	public PersoneDTO() {
	}

	public PersoneDTO(Integer id, String login, String name, Integer owner, Integer level, List<Integer> levels,
					  Boolean blocked) {
		this.id = id;
		this.login = login;
		this.name = name;
		this.owner = owner;
		this.level = level;
		this.levels = levels;
		this.blocked = blocked;

		set("id", id);
		set("login", login);
		set("name", name);
		set("owner", owner);
		set("level", level);
		set("levels", levels);
		set("blocked", blocked);
	}

	@Override
	public void submit() {
		id = checkInteger("id");
		login = checkString("login");
		name = checkString("name");
		owner = checkInteger("owner");
		level = checkInteger("level");
		levels = get("levels");
		blocked = checkBoolean("blocked");
	}

	public String toString() {
		return "id:" + id + ";login:" + login + ";name:" + name + ";owner:" + owner + ";level:" + level + ";blocked:" +
				blocked;
	}

	public static List<PersoneDTO> getPersones(int from, int to) {
		List<PersoneDTO> persones = new ArrayList<PersoneDTO>();

		for (int i = from; i < to; i++) {
			persones.add(new PersoneDTO(i, "login" + i, "name" + i, i % 10, i % 10, Arrays.asList(1, 2, 3, 4),
					i % 2 == 0));
		}

		return persones;
	}
}
