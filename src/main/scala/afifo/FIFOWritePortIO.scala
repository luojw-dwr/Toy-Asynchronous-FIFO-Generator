package afifo

import chisel3._
import chisel3.util._

class FIFOWritePortIO[T <: Data](private val gen: T) extends Bundle {
    val winc  = Output(Bool())
    val wfull = Input(Bool())
    val wbits = Output(gen.cloneType)
}
