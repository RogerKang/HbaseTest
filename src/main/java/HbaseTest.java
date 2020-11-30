
import org.apache.hadoop.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.Connection;

public class HbaseTest {
    public static void main(String[] args) {
        try {
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "hb-proxy-pub-bp1211s6t0uj7s1i8-001.hbase.rds.aliyuncs.com");
            config.set("hbase.zookeeper.property.clientPort", "2181");

            Connection connection = ConnectionFactory.createConnection(config);
            TableName tableName = TableName.valueOf("dept");

            //Table table = connection.getTable(TableName.valueOf("dept"));
            TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(tableName);
            builder.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("mycf")).build());
            TableDescriptor tableDescriptor = builder.build();
            //connection.getAdmin().createTable(tableDescriptor);
            Table table = connection.getTable(TableName.valueOf("dept"));
            Put put = new Put(Bytes.toBytes("row1"));
            put.addColumn(Bytes.toBytes("mycf"), Bytes.toBytes("name"), Bytes.toBytes("Jack"));
            table.put(put);
            Get get = new Get(Bytes.toBytes("row1"));
            Result result = table.get(get);
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("mycf"), Bytes.toBytes("name"))));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
