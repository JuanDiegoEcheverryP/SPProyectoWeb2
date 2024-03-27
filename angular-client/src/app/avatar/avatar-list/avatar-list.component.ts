import { Component } from '@angular/core';
import { AvatarService } from '../../shared/avatar.service';
import { Avatar } from '../../model/avatar';

@Component({
  selector: 'app-person-list',
  templateUrl: './avatar-list.component.html',
  styleUrl: './avatar-list.component.css'
})
export class AvatarListComponent {


  avatares: Avatar[] = [];

  constructor(
    private avatarService: AvatarService,
  ) { }

  ngOnInit(): void {
    this.avatarService.listarAvatares().subscribe(avatares => this.avatares = avatares)
  }
}
