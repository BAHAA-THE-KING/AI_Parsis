import java.util.Arrays;

public class Board {
    static int[] pathHuman = new int[84];
    static int[] pathComputer = new int[84];
//   static int[] pathComputer = {142, 123, 104, 85, 66, 47, 28, 9, 27, 45, 65, 84, 103, 122, 141, 159, 158, 157, 156, 155, 154, 153, 152, 171, 190, 190, 191, 192, 193, 194, 195, 196, 197, 217, 236, 255, 274, 293, 312, 331, 350, 351, 352, 333, 314, 295, 276, 257, 238, 219, 201, 202, 203, 204, 205, 206, 207, 208, 189, 170, 169, 168, 167, 166, 165, 164, 163, 143, 124, 105, 86, 67, 48, 29, 10, 9, 28, 47, 66, 85, 104, 123, 142, 161};

    static int[] safeCells = new int[8];

    static {
        pathHuman[0] = 218;


        pathHuman[50] = 159;
        pathHuman[51] = 158;
        pathHuman[52] = 157;
        pathHuman[53] = 156;
        pathHuman[54] = 155;
        pathHuman[55] = 154;
        pathHuman[56] = 153;
        pathHuman[57] = 152;
        pathHuman[58] = 171;
        pathHuman[59] = 190;
        pathHuman[60] = 191;
        pathHuman[61] = 192;
        pathHuman[62] = 193;
        pathHuman[63] = 194;
        pathHuman[64] = 195;
        pathHuman[65] = 196;
        pathHuman[66] = 197;
        pathHuman[67] = 217;
        pathHuman[68] = 236;
        pathHuman[69] = 255;
        pathHuman[70] = 274;
        pathHuman[71] = 293;
        pathHuman[72] = 312;
        pathHuman[73] = 331;
        pathHuman[74] = 350;
        pathHuman[75] = 351;
        pathHuman[76] = 332;
        pathHuman[77] = 313;
        pathHuman[78] = 294;
        pathHuman[79] = 275;
        pathHuman[80] = 256;
        pathHuman[81] = 237;
        pathHuman[82] = 218;
        pathHuman[83] = 199;

      

        pathComputer[0] = 142;
        pathComputer[1] = 123;
        pathComputer[2] = 104;
        pathComputer[3] = 85;
        pathComputer[4] = 66;
        pathComputer[5] = 47;
        pathComputer[6] = 28;
        pathComputer[7] = 9;
        pathComputer[8] = 27;
        pathComputer[9] = 45;
        pathComputer[10] = 65;
        pathComputer[11] = 84;
        pathComputer[12] = 103;
        pathComputer[13] = 122;
        pathComputer[14] = 141;
        pathComputer[15] = 159;
        pathComputer[16] = 158;
        pathComputer[17] = 157;
        pathComputer[18] = 156;
        pathComputer[19] = 155;
        pathComputer[20] = 154;
        pathComputer[21] = 153;
        pathComputer[22] = 152;
        pathComputer[23] = 171;
        pathComputer[24] = 190;
        pathComputer[25] = 190;
        pathComputer[26] = 191;
        pathComputer[27] = 192;
        pathComputer[28] = 193;
        pathComputer[29] = 194;
        pathComputer[30] = 195;
        pathComputer[31] = 196;
        pathComputer[32] = 197;
        pathComputer[33] = 217;
        pathComputer[34] = 236;
        pathComputer[35] = 255;
        pathComputer[36] = 274;
        pathComputer[37] = 293;
        pathComputer[38] = 312;
        pathComputer[39] = 331;
        pathComputer[40] = 350;
        pathComputer[41] = 351;
        pathComputer[42] = 352;
        pathComputer[43] = 333;
        pathComputer[44] = 314;
        pathComputer[45] = 295;
        pathComputer[46] = 276;
        pathComputer[47] = 257;
        pathComputer[48] = 238;
        pathComputer[49] = 219;
        pathComputer[50] = 201;
        pathComputer[51] = 202;
        pathComputer[52] = 203;
        pathComputer[53] = 204;
        pathComputer[54] = 205;
        pathComputer[55] = 206;
        pathComputer[56] = 207;
        pathComputer[57] = 208;
        pathComputer[58] = 189;
        pathComputer[59] = 170;
        pathComputer[60] = 169;
        pathComputer[61] = 168;
        pathComputer[62] = 167;
        pathComputer[63] = 166;
        pathComputer[64] = 165;
        pathComputer[65] = 164;
        pathComputer[66] = 163;
        pathComputer[67] = 143;
        pathComputer[68] = 124;
        pathComputer[69] = 105;
        pathComputer[70] = 86;
        pathComputer[71] = 67;
        pathComputer[72] = 48;
        pathComputer[73] = 29;
        pathComputer[74] = 10;
        pathComputer[75] = 9;
        pathComputer[76] = 28;
        pathComputer[77] = 47;
        pathComputer[78] = 66;
        pathComputer[79] = 85;
        pathComputer[80] = 104;
        pathComputer[81] = 123;
        pathComputer[82] = 142;
        pathComputer[83] = 161;


        safeCells[0] = 46;
        safeCells[1] = 48;
        safeCells[2] = 154;
        safeCells[3] = 192;
        safeCells[4] = 168;
        safeCells[5] = 206;
        safeCells[6] = 312;
        safeCells[7] = 314;

        //Those are IDs
        /*
                                       |___________|
          0   1   2   3   4   5   6   7|  8   9  10| 11  12  13  14  15  16  17  18
         19  20  21  22  23  24  25  26| 27  28  29| 30  31  32  33  34  35  36  37
         38  39  40  41  42  43  44  45| 46  47  48| 49  50  51  52  53  54  55  56
         57  58  59  60  61  62  63  64| 65  66  67| 68  69  70  71  72  73  74  75
         76  77  78  79  80  81  82  83| 84  85  86| 87  88  89  90  91  92  93  94
         95  96  97  98  99 100 101 102|103 104 105|106 107 108 109 110 111 112 113
        114 115 116 117 118 119 120 121|122 123 124|125 126 127 128 129 130 131 132
        133 134 135 136 137 138 139 140|141 142 143|144 145 146 147 148 149 150 151
       |-------------------------------|-----------|-------------------------------|
       |152 153 154 155 156 157 158 159|160 161 162|163 164 165 166 167 168 169 170|
       |171 172 173 174 175 176 177 178|179 180 181|182 183 184 185 186 187 188 189|
       |190 191 192 193 194 195 196 197|198 199 200|201 202 203 204 205 206 207 208|
       |-------------------------------|-----------|-------------------------------|
        209 210 211 212 213 214 215 216|217 218 219|220 221 222 223 224 225 226 227
        228 229 230 231 232 233 234 235|236 237 238|239 240 241 242 243 244 245 246
        247 248 249 250 251 252 253 254|255 256 257|258 259 260 261 262 263 264 265
        266 267 268 269 270 271 272 273|274 275 276|277 278 279 280 281 282 283 284
        285 286 287 288 289 290 291 292|293 294 295|296 297 298 299 300 301 302 303
        304 305 306 307 308 309 310 311|312 313 314|315 316 317 318 319 320 321 322
        323 324 325 326 327 328 329 330|331 332 333|334 335 336 337 338 339 340 341
        342 343 344 345 346 347 348 349|350 351 352|353 354 355 356 357 358 359 360
                                       |-----------|
        */
    }

    int[] piecesHuman = new int[4];
    int[] piecesComputer = new int[4];

    public Board() {
        piecesHuman[0] = -1;
        piecesHuman[1] = -1;
        piecesHuman[2] = -1;
        piecesHuman[3] = -1;
        piecesComputer[0] = -1;
        piecesComputer[1] = -1;
        piecesComputer[2] = -1;
        piecesComputer[3] = -1;
    }

    public Board(int[] piecesHuman, int[] piecesComputer) {
        this.piecesHuman = Arrays.copyOf(piecesHuman, 4);
        this.piecesComputer = Arrays.copyOf(piecesComputer, 4);
    }

    public boolean isSafe(int id){
        return Arrays.asList(safeCells).contains(id);
    }
}
