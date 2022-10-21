package rus.ama.smskod

import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class MCrypt {
    private val iv = "fedcba9876543210"
  lateinit var ivspec: IvParameterSpec
    lateinit var keyspec: SecretKeySpec
    lateinit var cipher: Cipher
    private val SecretKey = "0123456789abcdef"

    fun encrypt(strToEncrypt: String): ByteArray {
        val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
        //val keygen = KeyGenerator.getInstance("AES")
        //keygen.init(256)
      //  val key = SecretKey//keygen.generateKey()
        ivspec = IvParameterSpec(iv.toByteArray())
        keyspec = SecretKeySpec(SecretKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec)
        val cipherText = cipher.doFinal(plainText)

        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toChar())
        }
        return cipherText
    }

    fun encrypt2(text: String): ByteArray? {
        if (text == null || text.length == 0) throw Exception("Empty string")
        var encrypted: ByteArray? = null
        encrypted = try {
            ivspec = IvParameterSpec(iv.toByteArray())
            keyspec = SecretKeySpec(SecretKey.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.doFinal(padString(text).toByteArray())
        } catch (e: Exception) {
            throw Exception("[encrypt] " + e.message)
        }
        return encrypted
    }
/*
2022-10-21 13:56:16.249 12713-12713/rus.ama.smskod E/txt: широта 55.34, longitude 44.22
2022-10-21 13:56:16.252 12713-12713/rus.ama.smskod E/
encrypted: 8bb0e3e0d61481669011d157b5139148ef11e83bf12b13cc4d9fccc116157d5dd16317b9c116145173125135ae10612b163ac8d1129f13616ab2e1101
* */
    @Throws(Exception::class)
    fun decrypt(code: String?): ByteArray? {
        if (code == null || code.isEmpty()) throw Exception("Empty string")
        var decrypted: ByteArray? = null
        decrypted = try {
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec)
            cipher.doFinal(/*hexToBytes(*/code.toByteArray(Charsets.UTF_8))
        } catch (e: Exception) {
            throw Exception("[decrypt] " + e.message)
        }
        return decrypted
    }

    companion object {
        fun bytesToHex(data: ByteArray?): String? {
            if (data == null) {
                return null
            }
            val len = data.size
            var str = ""
            for (i in 0 until len) {
                str =
                    if (data[i] + 0xFF < 16) str + "0" + Integer.toHexString(data[i] + 0xFF)
                    else str + Integer.toHexString(
                        data[i] + 0xFF
                    )
            }
            return str
        }
//https://www.androidsnippets.com/encrypt-decrypt-between-android-and-php.html
        fun hexToBytes(str: String?): ByteArray? {
            return if (str == null) {
                null
            } else if (str.length < 2) {
                null
            } else {
                val len = str.length / 2
                val buffer = ByteArray(len)
                for (i in 0 until len) {
                    buffer[i] = str.substring(i * 2, i * 2 + 2).toInt(16).toByte()
                }
                buffer
            }
        }

        private fun padString(source: String): String {
            var source = source
            val paddingChar = ' '
            val size = 16
            val x = source.length % size
            val padLength = size - x
            for (i in 0 until padLength) {
                source += paddingChar
            }
            return source
        }
    }

    init {
        ivspec = IvParameterSpec(iv.toByteArray())
        keyspec = SecretKeySpec(SecretKey.toByteArray(), "AES")
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        } catch (e: NoSuchAlgorithmException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}