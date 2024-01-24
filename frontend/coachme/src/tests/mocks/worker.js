import { setupWorker } from 'msw/browser'
import memberHandler from './handler/member'
import adminHandler from './handler/admin'

export const worker = setupWorker(...memberHandler, ...adminHandler)
