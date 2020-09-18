package afifo

import chisel3._
import chisel3.util._

class FIFOReadPortIO[T <: Data](private val gen: T) extends Bundle {
    val rinc   = Output(Bool())
    val rempty = Input(Bool())
    val rbits  = Input(gen.cloneType)
}
