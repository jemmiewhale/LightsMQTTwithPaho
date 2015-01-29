package zkhlnk.v.lightsmqttwithpahotestproject.utils;

public class TypesConverter {
    public static byte[] toByta(boolean[] data) {
        if (data == null) return null;

        int len = data.length;
        byte[] lena = toByta(len);
        byte[] byts = new byte[lena.length + (len / 8) + (len % 8 != 0 ? 1 : 0)];

        System.arraycopy(lena, 0, byts, 0, lena.length);

        for (int i = 0, j = lena.length, k = 7; i < data.length; i++) {
            byts[j] |= (data[i] ? 1 : 0) << k--;
            if (k < 0) {
                j++;
                k = 7;
            }
        }

        return byts;
    }

    public static byte[] toByta(int data) {
        return new byte[]{
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }

    public static boolean[] toBooleanA(byte[] data) {
        if (data == null || data.length < 4) return null;

        int len = toInt(new byte[]{data[0], data[1], data[2], data[3]});
        boolean[] bools = new boolean[len];

        for (int i = 0, j = 4, k = 7; i < bools.length; i++) {
            bools[i] = ((data[j] >> k--) & 0x01) == 1;
            if (k < 0) {
                j++;
                k = 7;
            }
        }

        return bools;
    }

    public static int toInt(byte[] data) {
        if (data == null || data.length != 4) return 0x0;

        return (0xff & data[0]) << 24 |
                (0xff & data[1]) << 16 |
                (0xff & data[2]) << 8 |
                (0xff & data[3]);
    }
}
