import com.awifi.bigscreen.bigscreen.entity.BigscreenReTypeEnum;

public class EnumTest {
	public static void main(String[] args) {
		System.out.print(BigscreenReTypeEnum.Second.name() + "	");
		System.out.print(BigscreenReTypeEnum.Second.getCode() + "	");
		System.out.println(BigscreenReTypeEnum.Second.getDisplay());

		BigscreenReTypeEnum[] useableEnums = BigscreenReTypeEnum.values();
		for (int i = 0; i < useableEnums.length; i++) {
			System.out.print(useableEnums[i].name() + "	");
			System.out.print(useableEnums[i].getCode() + "	");
			System.out.println(useableEnums[i].getDisplay());
		}
	}
}