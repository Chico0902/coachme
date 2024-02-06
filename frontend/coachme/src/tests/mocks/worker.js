import { setupWorker } from 'msw/browser'
import memberHandler from './handler/member'
import adminHandler from './handler/admin'
import authHandler from './handler/auth'

export const worker = setupWorker(...memberHandler, ...adminHandler, ...authHandler)
