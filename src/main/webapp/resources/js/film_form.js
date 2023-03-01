
          function effacer(component) {
               ($($($(component).parent()).parent()).parent()).remove();
          }

          function ajouter(liste_actor) {
               console.log('appender')
               var html = '<div class="row custom-shadow">'+
                                                       '<div class="col-md-4">'+
                                                            '<div class="form-group">'+
                                                                 '<label for="">Nom</label>'+
                                                                 '<input type="text" class="form-control"'+
                                                                      'placeholder="place" name="place">'+
                                                            '</div>'+
                                                       '</div>'+
                                                       '<div class="col-md-4">'+
                                                            '<div class="form-group">'+
                                                                 '<label for="">Genre</label>'+
                                                                 '<select name="type" class="form-control" id="idOption">'+
                                                                      '<option value="plateau1">male</option>'+
                                                                      '<option value="plateau1">female</option>'+

                                                                 '</select>'+
                                                            '</div>'+
                                                       '</div>'+
                                                       '<div class="col-md-2"></div>'+
                                                       '<div class="col-md-2">'+
                                                            '<div class="form-group">'+
                                                                 '<br/>'+
                                                                 '<button class="btn btn-danger" type="button"'+
                                                                      'onClick="effacer(this)">- enlever</button>'+
                                                            '</div>'+
                                                       '</div>'+
                                                       '<div class="col-md-6" id="dialogue_action">'+
                                                            '<div class="form-group">'+
                                                                 '<label for="">Acteur</label>'+
                                                                 '<select name="type" class="form-control" id="idOption">'+
                                                                      '<option value="plateau1">male</option>'+
                                                                      '<option value="plateau1">female</option>'+

                                                                 '</select>'+
                                                            '</div>'+
                                                       '</div>'+
                                                       '<div class="col-md-12" id="dialogue_dialogue">'+
                                                            '<div class="form-group">'+
                                                                 '<label for="">description</label>'+
                                                                 '<textarea class="form-control" placeholder="..."'+
                                                                      'rows="3" name="description"></textarea>'+
                                                            '</div>'+
                                                       '</div>'+
                                                  '<br/></div>'
               $("#personnage").append(html);                  
          }