function effacer(component) {
     ($($($(component).parent()).parent()).parent()).remove();
}

function ajouter(liste_perso) {
     console.log('appender')
     var html = '<div class="row custom-shadow" id="dialogue">' +
                    '<div class="col-md-12" id="dialogue_personnage">' +
                         '<label for="">Personnage</label>' + 
                              '<div class="form-group row" >'+                       
                                             '<select name="type" class="form-control col-md-2" id="idOption">'+
                                                  '<option value="plateau1">plateau1</option>'+
                                                  '<option value="plateau1">plateau2</option>'+
                                             '</select>'+
                                             '<div class="col-md-7"></div>'+
                                             '<button class="btn btn-danger col-md-3" type="button" onClick="effacer(this)">- enlever</button>'+
                              '</div >'+
                    '</div >'+
                    '<div class="col-md-6" id="dialogue_dialogue">'+
                                        '<div class="form-group">'+
                                             '<label for="">Dialogue</label>'+
                                             '<textarea class="form-control" placeholder="..." rows="3"'+
                                        'name="description"></textarea>'+
                                        '</div>'+
                    '</div>'+
                    '<div class="col-md-6" id="dialogue_action">'+
                                        '<div class="form-group">'+
                                             '<label for="">Action</label>'+
                                             '<textarea class="form-control" placeholder="..." rows="3"'+
                                        'name="description"></textarea>'+
                                        '</div>'+
                    '</div>'+
                 '</div > ';
     $("#dialogue").append(html);                  
}
