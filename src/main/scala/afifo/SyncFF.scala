package afifo

import chisel3._
import chisel3.util._

object SyncFF {
    def apply(i_async: UInt, n: Int, varname: String) = {
        (1 to n).foldLeft(i_async)((last_sync, idx) => {
            RegNext(last_sync, init=0.U).suggestName(s"${varname}_syncFF${idx}")
        })
    }
}
