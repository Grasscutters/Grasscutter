package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Mail {
	public static class ExcelMailType extends TwoArgFunction {
		public static final int EXCEL_MAIL_NONE = 0;
		public static final int EXCEL_MAIL_TOWER_DAILY = 1;
		public static final int EXCEL_MAIL_TOWER_MONTHLY = 2;
		public static final int EXCEL_MAIL_TOWER_OVERFLOW_FIRST_PASS = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstMail = new LuaTable();
			scriptConstMail.set("EXCEL_MAIL_NONE", LuaValue.valueOf(EXCEL_MAIL_NONE));
			scriptConstMail.set("EXCEL_MAIL_TOWER_DAILY", LuaValue.valueOf(EXCEL_MAIL_TOWER_DAILY));
			scriptConstMail.set("EXCEL_MAIL_TOWER_MONTHLY", LuaValue.valueOf(EXCEL_MAIL_TOWER_MONTHLY));
			scriptConstMail.set("EXCEL_MAIL_TOWER_OVERFLOW_FIRST_PASS", LuaValue.valueOf(EXCEL_MAIL_TOWER_OVERFLOW_FIRST_PASS));
			env.set("ExcelMailType", scriptConstMail);
			env.get("package").get("loaded").set("ExcelMailType", scriptConstMail);
			return env;
		}
	}
}
