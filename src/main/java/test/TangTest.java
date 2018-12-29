package test;

import org.mybatis.generator.api.ShellRunner;

/**
 * @author tang
 *         2016/10/07 16:58
 */
public class TangTest {


    public static void main(String[] args) throws Exception {

        // 调试初始化参数
        TangTest test = new TangTest();
        //取得根目录路径
        String rootPath = test.getClass().getResource("/").getFile().toString();
        //当前目录路径
        //String currentPath1=test.getClass().getResource(".").getFile().toString();
        //String currentPath2=test.getClass().getResource("").getFile().toString();
        //当前目录的上级目录路径
        //   String parentPath=test.getClass().getResource("../").getFile().toString();
        String[] arg = new String[]{"-configfile", rootPath + "test/generatorConfig.xml", "-overwrite"};
        //String[] arg = new String[]{"-configfile", rootPath + "test/generatorConfigForMySql.xml", "-overwrite"};

        ShellRunner.main(arg);

    }
}
