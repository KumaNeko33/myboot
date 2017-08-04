package com.shuai.test.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * @Title: EncryptionUtil.java
 * 
 * @Package net.showcoo.utils
 * 
 * @Description: TODO(加密工具)
 * 
 * @author ~(≧▽≦)/~ duerlatter@vip.qq.com
 * 
 * @date 2015-06-09 下午16:11:09
 * 
 * @version V1.0
 * 
 * @Signature 怒发冲冠，凭阑处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲，白了少年头，空悲切。
 *            靖康耻，犹未雪；臣子恨，何时灭。驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。
 */

public class EncryptionUtil {

	/**
	 * all set GLOBAL_AUTH_KEY
	 */
	public static String GLOBAL_AUTH_KEY = "d2a6f08c638a88c870a463118ccdf0b6";

	/**
	 * 字符串加密,支持中文
	 * 
	 * @param $string
	 * @param $expiry
	 * @return
	 */
	public static String authEncode(String $string, int $expiry) {
		char[] temp = Base64.encode($string.getBytes());
		$string = new String(temp);
		return EncryptionUtil.authcode($string, "ENCODE", "", $expiry);
	}

	/**
	 * 字符串解密
	 * 
	 * @param $string
	 * @return
	 */
	public static String authDecode(String $string) {
		String umd = EncryptionUtil.authcode($string, "DECODE", "", 0);
		byte[] unwait = Base64.decode(umd.toCharArray());
		return new String(unwait);
	}

	/**
	 * 字符串加密以及解密函数
	 * 
	 * @param string
	 *            $string 原文或者密文
	 * @param string
	 *            $operation 操作(ENCODE | DECODE), 默认为 DECODE
	 * @param string
	 *            $key 密钥
	 * @param int $expiry 密文有效期, 加密时候有效， 单位 秒，0 为永久有效
	 * @return string 处理后的 原文或者 经过 base64_encode 处理后的密文
	 * 
	 * @example
	 * 
	 *          $a = authcode('abc', 'ENCODE', 'key'); $b = authcode($a,
	 *          'DECODE', 'key'); // $b(abc)
	 * 
	 *          $a = authcode('abc', 'ENCODE', 'key', 3600); $b =
	 *          authcode('abc', 'DECODE', 'key'); // 在一个小时内，$b(abc)，否则 $b 为空
	 */
	private static String authcode(String $string, String $operation, String $key, int $expiry) {

		if ($string != null) {
			if ($operation.equals("DECODE")) {
				try {
					$string = $string.replaceAll("\\.0\\.", " ");
					$string = $string.replaceAll("\\.1\\.", "=");
					$string = $string.replaceAll("\\.2\\.", "+");
					$string = $string.replaceAll("\\.3\\.", "/");
				} catch (Exception ex) {
				}
			}
		}

		int $ckey_length = 4; // note 随机密钥长度 取值 0-32;
		// note 加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，加密结果也会每次不同，增大破解难度。
		// note 取值越大，密文变动规律越大，密文变化 = 16 的 $ckey_length 次方
		// note 当此值为 0 时，则不产生随机密钥

		$key = md5($key != null ? $key : GLOBAL_AUTH_KEY);
		String $keya = md5(substr($key, 0, 16));
		String $keyb = md5(substr($key, 16, 16));
		String $keyc = $ckey_length > 0 ? ($operation.equals("DECODE") ? substr($string, 0, $ckey_length) : substr(md5(microtime()), -$ckey_length)) : "";

		String $cryptkey = $keya + md5($keya + $keyc);
		int $key_length = $cryptkey.length();

		$string = $operation.equals("DECODE") ? base64_decode(substr($string, $ckey_length)) : sprintf("%010d", $expiry > 0 ? $expiry + time() : 0) + substr(md5($string + $keyb), 0, 16) + $string;
		int $string_length = $string.length();

		StringBuffer $result1 = new StringBuffer();

		int[] $box = new int[256];
		for (int i = 0; i < 256; i++) {
			$box[i] = i;
		}

		int[] $rndkey = new int[256];
		for (int $i = 0; $i <= 255; $i++) {
			$rndkey[$i] = (int) $cryptkey.charAt($i % $key_length);
		}

		int $j = 0;
		for (int $i = 0; $i < 256; $i++) {
			$j = ($j + $box[$i] + $rndkey[$i]) % 256;
			int $tmp = $box[$i];
			$box[$i] = $box[$j];
			$box[$j] = $tmp;
		}

		$j = 0;
		int $a = 0;
		for (int $i = 0; $i < $string_length; $i++) {
			$a = ($a + 1) % 256;
			$j = ($j + $box[$a]) % 256;
			int $tmp = $box[$a];
			$box[$a] = $box[$j];
			$box[$j] = $tmp;

			$result1.append((char) (((int) $string.charAt($i)) ^ ($box[($box[$a] + $box[$j]) % 256])));

		}

		if ($operation.equals("DECODE")) {
			String $result = $result1.substring(0, $result1.length());
			if ((Integer.parseInt(substr($result.toString(), 0, 10)) == 0 || Long.parseLong(substr($result.toString(), 0, 10)) - time() > 0) && substr($result.toString(), 10, 16).equals(substr(md5(substr($result.toString(), 26) + $keyb), 0, 16))) {
				return substr($result.toString(), 26);
			} else {
				return "";
			}
		} else {
			String str = $keyc + base64_encode($result1.toString());
			try {
				str = str.replaceAll(" ", ".0.");
				str = str.replaceAll("=", ".1.");
				str = str.replaceAll("\\+", ".2.");
				str = str.replaceAll("\\/", ".3.");
			} catch (Exception ex) {
			}
			return str;
		}
	}

	private static String md5(String input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return byte2hex(md.digest(input.getBytes()));
	}

	private static String md5(long input) {
		return md5(String.valueOf(input));
	}

	private static String base64_decode(String input) {
		try {
			return new String(Base64.decode(input.toCharArray()), "iso-8859-1");
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private static String base64_encode(String input) {
		try {
			return new String(Base64.encode(input.getBytes("iso-8859-1")));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString();
	}

	private static String substr(String input, int begin, int length) {
		return input.substring(begin, begin + length);
	}

	private static String substr(String input, int begin) {
		if (begin > 0) {
			return input.substring(begin);
		} else {
			return input.substring(input.length() + begin);
		}
	}

	private static long microtime() {
		return System.currentTimeMillis();
	}

	private static long time() {
		return System.currentTimeMillis() / 1000;
	}

	private static String sprintf(String format, long input) {
		String temp = "0000000000" + input;
		return temp.substring(temp.length() - 10);
	}

	public static void main(String[] args) {

		String json = "{\"userId\":1,\"pwd\":\"你好\"}";
		String encode = EncryptionUtil.authEncode(json, 0);
		System.err.println(encode);
		String decode = EncryptionUtil.authDecode(encode);
		System.err.println(decode);
	}

}

class Base64 {

	/**
	 * returns an array of base64-encoded characters to represent the passed
	 * data array.
	 * 
	 * @param data
	 *            the array of bytes to encode
	 * @return base64-coded character array.
	 */
	public static char[] encode(byte[] data) {
		char[] out = new char[((data.length + 2) / 3) * 4];

		//
		// 3 bytes encode to 4 chars. Output is always an even
		// multiple of 4 characters.
		//
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;

			int val = (0xFF & data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}

	/**
	 * Decodes a BASE-64 encoded stream to recover the original data. White
	 * space before and after will be trimmed away, but no other manipulation of
	 * the input will be performed.
	 * 
	 * As of version 1.2 this method will properly handle input containing junk
	 * characters (newlines and the like) rather than throwing an error. It does
	 * this by pre-parsing the input and generating from that a count of VALID
	 * input characters.
	 **/
	public static byte[] decode(char[] data) {
		// as our input could contain non-BASE64 data (newlines,
		// whitespace of any sort, whatever) we must first adjust
		// our count of USABLE data so that...
		// (a) we don't misallocate the output array, and
		// (b) think that we miscalculated our data length
		// just because of extraneous throw-away junk

		int tempLen = data.length;
		for (int ix = 0; ix < data.length; ix++) {
			if ((data[ix] > 255) || codes[data[ix]] < 0)
				--tempLen; // ignore non-valid chars and padding
		}
		// calculate required length:
		// -- 3 bytes for every 4 valid base64 chars
		// -- plus 2 bytes if there are 3 extra base64 chars,
		// or plus 1 byte if there are 2 extra.

		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3)
			len += 2;
		if ((tempLen % 4) == 2)
			len += 1;

		byte[] out = new byte[len];

		int shift = 0; // # of excess bits stored in accum
		int accum = 0; // excess bits
		int index = 0;

		// we now go through the entire array (NOT using the 'tempLen' value)
		for (int ix = 0; ix < data.length; ix++) {
			int value = (data[ix] > 255) ? -1 : codes[data[ix]];

			if (value >= 0)// skip over non-code
			{
				accum <<= 6; // bits shift up by 6 each time thru
				shift += 6; // loop, with new bits being put in
				accum |= value; // at the bottom.
				if (shift >= 8)// whenever there are 8 or more shifted in,
				{
					shift -= 8; // write them out (from the top, leaving any
					out[index++] = // excess at the bottom for next iteration.
					(byte) ((accum >> shift) & 0xff);
				}
			}
			// we will also have skipped processing a padding null byte ('=')
			// here;
			// these are used ONLY for padding to an even length and do not
			// legally
			// occur as encoded data. for this reason we can ignore the fact
			// that
			// no index++ operation occurs in that special case: the out[] array
			// is
			// initialized to all-zero bytes to start with and that works to our
			// advantage in this combination.
		}

		// if there is STILL something wrong we just have to throw up now!
		if (index != out.length) {
			throw new Error("Miscalculated data length (wrote " + index + " instead of " + out.length + ")");
		}

		return out;
	}

	//
	// code characters for values 0..63
	//
	private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

	//
	// lookup table for converting base64 characters to value in range 0..63
	//
	private static byte[] codes = new byte[256];

	static {
		for (int i = 0; i < 256; i++)
			codes[i] = -1;
		for (int i = 'A'; i <= 'Z'; i++)
			codes[i] = (byte) (i - 'A');
		for (int i = 'a'; i <= 'z'; i++)
			codes[i] = (byte) (26 + i - 'a');
		for (int i = '0'; i <= '9'; i++)
			codes[i] = (byte) (52 + i - '0');
		codes['+'] = 62;
		codes['/'] = 63;
	}

	// /////////////////////////////////////////////////
	// remainder (main method and helper functions) is
	// for testing purposes only, feel free to clip it.
	// /////////////////////////////////////////////////

	public static void main(String[] args) {
		boolean decode = false;

		if (args.length == 0) {
			System.out.println("usage:  java Base64 [-d[ecode]] filename");
			System.exit(0);
		}
		for (int i = 0; i < args.length; i++) {
			if ("-decode".equalsIgnoreCase(args[i]))
				decode = true;
			else if ("-d".equalsIgnoreCase(args[i]))
				decode = true;
		}

		String filename = args[args.length - 1];
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("Error:  file '" + filename + "' doesn't exist!");
			System.exit(0);
		}

		if (decode) {
			char[] encoded = readChars(file);
			byte[] decoded = decode(encoded);
			writeBytes(file, decoded);
		} else {
			byte[] decoded = readBytes(file);
			char[] encoded = encode(decoded);
			writeChars(file, encoded);
		}
	}

	private static byte[] readBytes(File file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			InputStream fis = new FileInputStream(file);
			InputStream is = new BufferedInputStream(fis);
			int count = 0;
			byte[] buf = new byte[16384];
			while ((count = is.read(buf)) != -1) {
				if (count > 0)
					baos.write(buf, 0, count);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}

	private static char[] readChars(File file) {
		CharArrayWriter caw = new CharArrayWriter();
		try {
			Reader fr = new FileReader(file);
			Reader in = new BufferedReader(fr);
			int count = 0;
			char[] buf = new char[16384];
			while ((count = in.read(buf)) != -1) {
				if (count > 0)
					caw.write(buf, 0, count);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return caw.toCharArray();
	}

	private static void writeBytes(File file, byte[] data) {
		try {
			OutputStream fos = new FileOutputStream(file);
			OutputStream os = new BufferedOutputStream(fos);
			os.write(data);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeChars(File file, char[] data) {
		try {
			Writer fos = new FileWriter(file);
			Writer os = new BufferedWriter(fos);
			os.write(data);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// /////////////////////////////////////////////////
	// end of test code.
	// /////////////////////////////////////////////////

}
